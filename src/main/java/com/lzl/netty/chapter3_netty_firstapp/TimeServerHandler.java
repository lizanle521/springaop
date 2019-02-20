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
        // big endian 是高位地址存在 起始字节上，叫网络字节序
        // 这个bytebuf是bytebuffer的包装类，采用的是big endian字节序编码保存字节流
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
