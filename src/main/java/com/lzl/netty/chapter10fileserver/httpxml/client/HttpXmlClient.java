package com.lzl.netty.chapter10fileserver.httpxml.client;

import com.lzl.netty.chapter10fileserver.httpxml.Order;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlRequestDecoder;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlRequestEncoder;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlResponseDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author lizanle
 * @Date 2019/2/27 14:56
 */
public class HttpXmlClient {

    public void connect(String host,int port) throws Exception {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            // 客户端 则是响应解析排第一
                            ch.pipeline().addLast("http-decoder",
                                    new HttpResponseDecoder());
                            ch.pipeline().addLast("http-aggregator",
                                    new HttpObjectAggregator(65536));

                            ch.pipeline().addLast("xml-decoder",
                                    new HttpXmlResponseDecoder(Order.class,true));

                            ch.pipeline().addLast("http-encoder",
                                    new HttpRequestEncoder());
                            ch.pipeline().addLast("xml-encode",
                                    new HttpXmlRequestEncoder());

                            ch.pipeline().addLast(new HttpXmlClientHandler());

                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();

            future.channel().closeFuture().sync();
        } finally {
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
        new HttpXmlClient().connect("127.0.0.1",port);
    }
}
