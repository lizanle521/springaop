package com.lzl.netty.chapter12_privateprotocol.server;

import com.lzl.netty.chapter12_privateprotocol.codec.NettyMessageDecoder;
import com.lzl.netty.chapter12_privateprotocol.codec.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author lizanle
 * @data 2019/3/1 5:16 PM
 */
public class NettyServer {

    public void bind(int port) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
                            ch.pipeline().addLast(new NettyMessageEncoder());

                            ch.pipeline().addLast(new ReadTimeoutHandler(50));

                            ch.pipeline().addLast(new LoginAuthRespHandler());
                            ch.pipeline().addLast(new HeartBeatRespHandler());
                        }
                    });

        bootstrap.bind(port).sync();


    }

    public static void main(String[] args) throws Exception {
        new NettyServer().bind(8081);
    }
}
