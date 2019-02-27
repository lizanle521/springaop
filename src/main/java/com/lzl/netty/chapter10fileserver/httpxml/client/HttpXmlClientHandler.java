package com.lzl.netty.chapter10fileserver.httpxml.client;

import com.lzl.netty.chapter10fileserver.httpxml.OrderFactory;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlRequest;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author lizanle
 * @Date 2019/2/27 15:05
 */
public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
        ctx.writeAndFlush(request);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
        System.out.println("the client received headers is："+msg.getResponse().headers().names());

        System.out.println("the client received order response :"+msg.getResult());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
