package com.leo.core;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.stream.ChunkedStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.Servlet;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServletNettyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static Logger log = LoggerFactory.getLogger("com.leo.ServerStart");

    private Servlet servlet;
    private String charset;

    public ServletNettyHandler(Servlet servlet, String charset) {
        this.servlet = servlet;
        this.charset = charset;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        try {
            if (fullHttpRequest.decoderResult().isFailure()) {
                sendError(ctx, HttpResponseStatus.BAD_REQUEST);
                return;
            }

            // 创建模拟的ServletRequest、ServletResponse对象
            MockHttpServletRequest servletRequest = createHttpRequest(ctx, fullHttpRequest);
            MockHttpServletResponse servletResponse = new MockHttpServletResponse();

            // 调用service执行业务逻辑
            this.servlet.service(servletRequest, servletResponse);

            // 响应数据
            HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
            for (Object name : servletResponse.getHeaderNames()) {
                for (Object value : servletResponse.getHeaders((String) name)) {
                    response.headers().add((String) name, value);
                }
            }
            byte[] responseContent = servletResponse.getContentAsByteArray();
            int code = response.status().code();
            if (!(code >= 200 && code <= 299)) {
                responseContent = (code + " " + response.status().reasonPhrase()
                        + ", error: " + servletResponse.getErrorMessage()).getBytes(charset);
            }
            writeAndFlush(ctx, response, responseContent);

            // 打印日志
            if( MediaType.APPLICATION_OCTET_STREAM_VALUE.equals( servletResponse.getHeader("Content-Type") ) ){
                log.debug("response content: " + "正在下载文件...");
            }else{
                log.debug("response content: " + new String(responseContent, charset));
            }
        } catch (Exception e) {
            exceptionCaught(ctx, e.getCause());
        }
    }

    private MockHttpServletRequest createHttpRequest(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws IOException {
        Map<String, String>  paramMap = getRequestParams(ctx, fullHttpRequest);
        // 模拟一个ServletRequest对象
        final MockHttpServletRequest servletRequest = new MockHttpServletRequest(servlet.getServletConfig().getServletContext());

        String uri = fullHttpRequest.uri();
        uri = new String(uri.getBytes("ISO8859-1"), charset);
        uri = URLDecoder.decode(uri, charset);
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();
        String path = uriComponents.getPath();
        path = URLDecoder.decode(path, charset);

        servletRequest.setRequestURI(path);
        servletRequest.setServletPath(path);
        servletRequest.setPathInfo(path);
        servletRequest.setMethod(fullHttpRequest.method().name());

        if (uriComponents.getScheme() != null) {
            servletRequest.setScheme(uriComponents.getScheme());
        }
        if (uriComponents.getPort() != -1) {
            servletRequest.setServerPort(uriComponents.getPort());
        }
        if (uriComponents.getHost() != null) {
            servletRequest.setServerName(uriComponents.getHost());
        }

        //请求头
        for (String name : fullHttpRequest.headers().names()) {
            for (String value : fullHttpRequest.headers().getAll(name)) {
                servletRequest.addHeader(name, value);
            }
        }

        //请求内容
        ByteBuf content = fullHttpRequest.content();
        content.readerIndex(0);
        byte[] data = new byte[content.readableBytes()];
        content.readBytes(data);
        servletRequest.setContent(data);

        //请求参数
        if (uriComponents.getQuery() != null) {
            String query = UriUtils.decode(uriComponents.getQuery(), charset);
            servletRequest.setQueryString(query);
        }
        if(paramMap != null && paramMap.size() > 0) {
            for (String keyTmp : paramMap.keySet()) {
                String key = UriUtils.decode(keyTmp, charset);
                String value  = UriUtils.decode(paramMap.get(keyTmp) == null ? "": paramMap.get(keyTmp), charset);
                servletRequest.addParameter(key, value);
            }
        }

        // 打印日志
        log.debug("request uri: " + uriComponents.getPath()
                + " method: " + fullHttpRequest.method().name()
                + " params: " + JSON.toJSONString(paramMap));
        String bodyParams = new String(data, charset);
        if( "application/json".equals( servletRequest.getHeader("Content-Type") ) ){
            log.debug("request body: \n" + bodyParams.trim());
        }

        return servletRequest;
    }

    /**
     * 获取post请求、get请求的参数保存到map中
     */
    private Map<String, String> getRequestParams(ChannelHandlerContext ctx, FullHttpRequest req) throws IOException {
        Map<String, String> requestParams = new HashMap<String, String>();

        {// 处理uri参数，一般为GET请求
            QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
            Map<String, List<String>> param = decoder.parameters();
            Iterator<Map.Entry<String, List<String>>> iterator = param.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> next = iterator.next();
                requestParams.put(next.getKey(), next.getValue().get(0));
            }
        }

        // 处理表单参数，POST请求, 不支持上传文件
        if (req.method() == HttpMethod.POST) {
            HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); //Disk
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory, req);
            decoder.offer(req);
            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
            for (InterfaceHttpData parm : parmList) {
                Attribute data = (Attribute) parm;
                requestParams.put(data.getName(), data.getValue());
            }
        }
        return requestParams;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if ( ctx.channel().isActive() ) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 响应错误信息
     * @param ctx
     * @param status
     */
    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) throws UnsupportedEncodingException {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
        response.headers().add(io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        int code = response.status().code();
        byte[] responseContent = (code + " " + response.status().reasonPhrase()).getBytes();
        writeAndFlush(ctx, response, responseContent);
        log.debug("response content: " + new String(responseContent, charset));
    }

    private void writeAndFlush(ChannelHandlerContext ctx, HttpResponse response, byte[] content){
        ctx.write(response);
        InputStream inputStream = new ByteArrayInputStream(content);
        ChannelFuture channelFuture = ctx.writeAndFlush(new ChunkedStream(inputStream));
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

}
