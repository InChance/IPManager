package com.leo.core;

import io.netty.bootstrap.ServerBootstrap;
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

    private NioEventLoopGroup bossGroup = null;
    private NioEventLoopGroup workGroup = null;

    private void run(){
        bossGroup = new NioEventLoopGroup(0, Executors.newCachedThreadPool());// boss线程组
        workGroup = new NioEventLoopGroup(0, Executors.newCachedThreadPool());// work线程组
        ServerBootstrap boot = new ServerBootstrap();
        boot.group(bossGroup, workGroup);
        boot.channel(NioServerSocketChannel.class);
        boot.childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("codec", new HttpServerCodec());
                pipeline.addLast("aggegator", new HttpObjectAggregator(64 * 1024 * 1024));
                pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
                pipeline.addLast("handler", new ServletNettyHandler( dispatcherServlet, serverConfig.getCharset() ));
            }
        });
        boot.bind(new InetSocketAddress(serverConfig.getPort()));
        log.debug("Server is listening on "+serverConfig.getPort()+"...");
    }

    public void shutdown() {
        if (bossGroup != null && workGroup != null) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @PostConstruct
    public void start(){
        log.debug("Class ServerBoot is created.");
        this.run();
    }

}
