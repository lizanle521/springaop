package com.lzl.netty.chapter10fileserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author lizanle
 * @Date 2019/2/25 14:43
 */
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src/main/java/com/lzl";

    public void run(final int port,final String url) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            // http请求消息解码器
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            // 将多个消息转换为FullHttpRequest 或者 HttpFullResponse
                            // 原因是消息解码器会在一个http请求中会生成多个消息对象
                            // 1. HttpRequest/HttpResponse  2.HttpContent 3 LastHttpContent
                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65535));
                            // 对http响应消息进行编码
                            ch.pipeline().addLast("http-encode",new HttpResponseEncoder());
                            // 支持异步发送大的码流，但不占用过多的内存
                            ch.pipeline().addLast("http-trunked",new ChunkedWriteHandler());

                            ch.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
                        }
                    });

            ChannelFuture future = bootstrap.bind(port).sync();

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
            } catch (NumberFormatException e) {

            }
        }
        System.out.println(System.getProperty("user.dir"));
        new HttpFileServer().run(port,DEFAULT_URL);
    }
}
