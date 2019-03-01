package com.lzl.netty.chapter12_privateprotocol.client;

import com.lzl.netty.chapter12_privateprotocol.codec.NettyMessageDecoder;
import com.lzl.netty.chapter12_privateprotocol.codec.NettyMessageEncoder;
import com.lzl.netty.chapter12_privateprotocol.server.LoginAuthRespHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lizanle
 * @data 2019/3/1 5:35 PM
 */
public class NettyClient {
    private final static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    public void connect(String host,int port) throws Exception {

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
                            ch.pipeline().addLast(new NettyMessageEncoder());

                            ch.pipeline().addLast(new ReadTimeoutHandler(50));

                            ch.pipeline().addLast(new LoginAuthReqHandler());
                            ch.pipeline().addLast(new HeartBeatReqHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();

            future.channel().closeFuture().sync();
        } finally {
            scheduledExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        try {
                            connect("127.0.0.1",8081);//发起重连
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyClient().connect("127.0.0.1",8081);
    }
}
