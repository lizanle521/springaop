package com.lzl.netty.chapter3_netty_firstapp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lizanle
 * @data 2019/2/19 9:09 PM
 */
public class TimeServer {

    public static void main(String[] args) throws Exception {
        int port = 8082;
        if(args != null && args.length > 0){
            try {
                Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        new TimeServer().bind(port);
    }
    public void bind(int port) throws Exception {
        // 配置服务端线程组，一个用来接收客户端的连接，一个用于socketchannel的网络读写
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 启动nio服务器的辅助类，用于降低服务端的开发复杂度
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024) // tcp握手成功队列
                    .childHandler(new ChildChannelHandler());

            // 绑定端口，同步等待成功
            ChannelFuture fu = bootstrap.bind(port).sync();

            // 等待服务器监听端口关闭
            fu.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 线程优雅退出
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }
}
