package com.lzl.netty.chapter10fileserver.httpxml.server;

import com.lzl.netty.chapter10fileserver.httpxml.Order;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlRequestDecoder;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlResponseDecoder;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;

/**
 * @author lizanle
 * @Date 2019/2/27 14:37
 */
public class HttpXmlServer {

    public void bind(int port) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer(){
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast("http-frame-decoder",
                                    new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator",
                                    new HttpObjectAggregator(65535));

                            ch.pipeline().addLast("xml-decoder",
                                    new HttpXmlRequestDecoder(Order.class,true));

                            ch.pipeline().addLast("response-encoder",
                                    new HttpResponseEncoder());
                            ch.pipeline().addLast("xml-response-encoder",
                                    new HttpXmlResponseEncoder());

                            ch.pipeline().addLast(new HttpXmlServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8081;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        new HttpXmlServer().bind(port);

    }
}
