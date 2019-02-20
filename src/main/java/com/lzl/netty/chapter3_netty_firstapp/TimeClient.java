package com.lzl.netty.chapter3_netty_firstapp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lizanle
 * @Date 2019/2/20 14:17
 */
public class TimeClient {

    public static void main(String[] args) throws Exception {
        int port = 8082;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        new TimeClient().connect("127.0.0.1",port);
    }

    public void connect(String host, int port) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new TimeClientHandler());
                    }
                });

        // 发起异步链接操作
        ChannelFuture f = bootstrap.connect(host, port).sync();
        //等待客户端关闭链路
        f.channel().closeFuture().sync();
    }
}