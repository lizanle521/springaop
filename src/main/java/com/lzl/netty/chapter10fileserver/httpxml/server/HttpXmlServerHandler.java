package com.lzl.netty.chapter10fileserver.httpxml.server;

import com.lzl.netty.chapter10fileserver.httpxml.Order;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlRequest;
import com.lzl.netty.chapter10fileserver.httpxml.codec.HttpXmlResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author lizanle
 * @Date 2019/2/27 14:45
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlRequest xmlRequest) throws Exception {
        FullHttpRequest request = xmlRequest.getRequest();
        Order order = (Order) xmlRequest.getBody();
        System.out.println("received order :"+order);

        doBusiness(order);
        ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, order));
        if(!HttpHeaders.isKeepAlive(request)){
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("operation complete!");
                    ctx.close();
                }
            });
        }

    }

    private void doBusiness(Order order) {
        System.out.println("do sth. with order:"+order);
    }
}
