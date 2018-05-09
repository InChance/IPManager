package com.leo.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * HTTP服务器启动类
 */
public class ServerBoot {

    private static Logger log = LoggerFactory.getLogger("com.leo.ServerStart");

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private DispatcherServlet dispatcherServlet;

    private NioEventLoopGroup bossGroup    = null;
    private NioEventLoopGroup workerGroup  = null;
    private ChannelFuture httpServerFuture = null;

    private void run(){
        try {
            bossGroup = new NioEventLoopGroup(0, Executors.newCachedThreadPool());// boss线程组
            workerGroup = new NioEventLoopGroup(0, Executors.newCachedThreadPool());// work线程组
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workerGroup);
            boot.channel(NioServerSocketChannel.class);
            boot.childHandler(new ChannelInitializer<SocketChannel>(){
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    /*HTTP 服务的解码器*/
                    pipeline.addLast("codec", new HttpServerCodec());
                    /*HTTP 消息的合并处理*/
                    pipeline.addLast("aggegator", new HttpObjectAggregator(64 * 1024 * 1024));
                    /*支持异步发送大的码流(例如大文件传输),但不占用过多的内存,防止JAVA内存溢出*/
                    pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
                    /*自己写的服务器逻辑处理*/
                    pipeline.addLast("handler", new ServletNettyHandler( dispatcherServlet, serverConfig.getCharset() ));
                }
            });
            httpServerFuture = boot.bind(new InetSocketAddress(serverConfig.getPort()));
            log.debug("Server is listening on "+serverConfig.getPort()+"...");
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @PostConstruct
    public void start(){
        this.run();
        log.debug("Class ServerBoot is created.");
        ServerBoot self = this;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                self.stop();
            }
        });
    }

    /**
     * 关闭服务器的时候释放Netty线程池相关资源
     */
    public void stop(){
        try{
            if(httpServerFuture != null){
                log.debug("netty server stop begin!");
                httpServerFuture.channel().close();
                httpServerFuture.channel().closeFuture().sync();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            log.debug("netty server stop complete!");
        }
    }

}
