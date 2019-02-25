package com.lzl.netty.chapter7_msgpack.block;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author lizanle
 * @data 2019/2/22 8:22 PM
 */
public class EchoClient {

    private final String host;

    private final int port;

    private final int sendNumber;

    public EchoClient(String host, int port, int sendNumber) {
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }

    public void run() throws Exception {
        // 配置线程池
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .handler(new ChannelInitializer(){
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            // 用于处理半包消息
                            ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(
                                    65535,0,2,0,2));
                            // 先添加解码器
                            ch.pipeline().addLast(new MsgpackDecoder());
                            // 在编码器前添加 LengthFieldPrepender，将在bytebuffer之前增加2字节的消息长度字段
                            ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                            // 然后添加编码器
                            ch.pipeline().addLast(new MsgpackEncoder());
                            ch.pipeline().addLast(new EchoClientHandler(sendNumber));

                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1",8081,1).run();
    }
}
