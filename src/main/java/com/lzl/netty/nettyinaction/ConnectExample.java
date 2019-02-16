package com.lzl.netty.nettyinaction;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;


/**
 * @author lizanle
 * @Date 2019/2/15 17:18
 */
public class ConnectExample {
    private final static Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * 连接一台服务器的某个端口并写 hello 字符串
     */
    public static void connect(){
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        // 不要阻塞
        ChannelFuture connect = channel.connect(new InetSocketAddress("192.168.10.59", 25));

        connect.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    ByteBuf buffer = Unpooled.copiedBuffer("hello", Charset.defaultCharset());
                    ChannelFuture future = channelFuture.channel().writeAndFlush(buffer);
                }else{
                    Throwable cause = channelFuture.cause();
                    cause.printStackTrace();
                }
            }
        });
    }

    public void connectDemo(){
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ChannelFuture connect = channel.connect(new InetSocketAddress("127.0.0.1", 25));
        GenericFutureListener<? extends Future<? super Void>> futureListener = new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    ByteBuf hello = Unpooled.copiedBuffer("hello", Charset.defaultCharset());
                    System.out.println("future is success");
                    channelFuture.channel().writeAndFlush(hello);
                }else if(channelFuture.isCancelled()){
                    System.out.println("future is cancelled");
                }else if(channelFuture.isDone()){
                    System.out.println("future is done");
                }else if(channelFuture.isVoid()){
                    System.out.println("future is void");
                }else if(channelFuture.cause() != null){
                    channelFuture.cause().printStackTrace();
                }
            }
        };
        connect.addListener(futureListener);

    }

    @Test
    public void test(){
        connect();
    }
}
