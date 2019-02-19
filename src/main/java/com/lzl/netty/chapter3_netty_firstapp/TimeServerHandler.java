package com.lzl.netty.chapter3_netty_firstapp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author lizanle
 * @data 2019/2/19 9:15 PM
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);

        String s = new String(bytes, "utf-8");
        System.out.println("time server received order:"+s);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(s) ? System.currentTimeMillis() + "" : "BAD ORDER";
        ByteBuf byteBuf = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
