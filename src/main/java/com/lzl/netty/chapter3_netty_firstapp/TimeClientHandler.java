package com.lzl.netty.chapter3_netty_firstapp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lizanle
 * @Date 2019/2/20 14:23
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    private final  Logger logger = LoggerFactory.getLogger(getClass());

    private final ByteBuf msg;
    public TimeClientHandler() {
        byte[] req = "QUERY TIME ORDER".getBytes();
        // 书上写的是Unpooled.buffer(req.length),这里我暂时不改，看看是否有bug
        msg = Unpooled.copiedBuffer(req);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String s = new String(bytes, "utf-8");
        System.out.println("Now is:"+s);
    }

    /**
     * tcp连接建立以后就会调用这个
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
