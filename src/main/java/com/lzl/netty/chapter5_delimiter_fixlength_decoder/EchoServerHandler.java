package com.lzl.netty.chapter5_delimiter_fixlength_decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author lizanle
 * @Date 2019/2/21 12:34
 */
public class EchoServerHandler extends ChannelHandlerAdapter {
    private int counter = 0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String req = (String) msg;
        System.out.println("this is " + ++counter + " times receive client : "+ req);
        req += "$_";
        ByteBuf byteBuf = Unpooled.copiedBuffer(req.getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
