package com.lzl.netty.chapter4_tcpmsgpackage.packetsplicing;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author lizanle
 * @Date 2019/2/21 9:05
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String body = new String(bytes,"utf-8").substring(0,
                bytes.length - System.getProperty("line.separator").length());

        System.out.println("The time server received order:"+body+",the counter is "+counter++);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? System.currentTimeMillis() + "" : "BAD ORDER";
        currentTime += System.getProperty("line.separator");

        ByteBuf result = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
