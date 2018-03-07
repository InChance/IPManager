package com.leo.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.Servlet;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
        if (fullHttpRequest.decoderResult().isFailure()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        // 模拟ServletRequest、ServletResponse对象
        MockHttpServletRequest servletRequest = createHttpRequest(fullHttpRequest);
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();
        try {
            // 调用service执行业务逻辑
            this.servlet.service(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
        for (Object name : servletResponse.getHeaderNames()) {
            for (Object value : servletResponse.getHeaders((String) name)) {
                response.headers().add((String) name, value);
            }
        }
        byte[] responseContent = servletResponse.getContentAsByteArray();
        if (response.status().code() != 200) {
            responseContent = (response.status().code() + " " + response.status().reasonPhrase()
                    + "  error:" + servletResponse.getErrorMessage()).getBytes(charset);
        }
        ctx.write(response);
        InputStream inputStream = new ByteArrayInputStream(responseContent);
        ChannelFuture channelFuture = ctx.writeAndFlush(new ChunkedStream(inputStream));
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    private MockHttpServletRequest createHttpRequest(FullHttpRequest fullHttpRequest){
        // 模拟一个ServletRequest对象
        final MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(fullHttpRequest.uri()).build();
        servletRequest.setRequestURI(uriComponents.getPath());
        servletRequest.setPathInfo(uriComponents.getPath());
        servletRequest.setMethod(fullHttpRequest.method().name());

        log.debug("request uri: " + uriComponents.getPath() + " | method: " + fullHttpRequest.method().name());

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
            servletRequest.addHeader(name, fullHttpRequest.headers().get(name));
        }
        //请求内容
        ByteBuf content = fullHttpRequest.content();
        if (content != null && content.hasArray()) {
            byte [] byteContent = content.array();
            servletRequest.setContent(byteContent);
        }
        //请求参数
        try {
            if (uriComponents.getQuery() != null) {
                String query = UriUtils.decode(uriComponents.getQuery(), charset);
            }
            for (java.util.Map.Entry<String, List<String>> entry : uriComponents.getQueryParams().entrySet()) {
                for (String value : entry.getValue()) {
                    servletRequest.addParameter(UriUtils.decode(entry.getKey(), charset), UriUtils.decode(value, charset));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return servletRequest;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 响应错误信息
     * @param ctx
     * @param status
     */
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        response.headers().add(io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
    }

}
