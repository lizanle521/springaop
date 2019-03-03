package com.lzl.netty.chapter12_privateprotocol.codec;

import io.netty.channel.*;

/**
 * @author lizanle
 * @data 2019/3/3 10:54 PM
 */
public class ExceptionHandler extends ChannelHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg,promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.cause() != null){
                    // 所有出栈消息的失败都要通过监听
                    future.cause().printStackTrace();
                }
            }
        }));
    }
}
