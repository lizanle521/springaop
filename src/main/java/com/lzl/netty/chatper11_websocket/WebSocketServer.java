package com.lzl.netty.chatper11_websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

/**
 * @author lizanle
 * @data 2019/2/27 8:24 PM
 */
public class WebSocketServer {

    public void run(int port) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-codec",
                                    new HttpServerCodec());
                            ch.pipeline().addLast("aggregator",
                                    new HttpObjectAggregator(65536));

                            ch.pipeline().addLast("http-chunked",
                                    new ChunkedWriteHandler());
                            ch.pipeline().addLast("handler",
                                    new WebSocketServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(new InetSocketAddress(port)).sync();

            future.channel().closeFuture().sync();
        } catch (Exception e) {

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8081;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            } finally {

            }
        }
        new WebSocketServer().run(port);
    }
}
