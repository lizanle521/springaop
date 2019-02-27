package com.lzl.netty.chatper11_websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author lizanle
 * @data 2019/2/27 8:30 PM
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            // http请求
            handleHttpRequest(ctx,(FullHttpRequest) msg);
        }else if(msg instanceof WebSocketFrame){
            handleWebSocket(ctx,(WebSocketFrame)msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame msg) {
        // 判断是否关闭链路的指令
        if(msg instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(),(CloseWebSocketFrame)msg.retain());
            return;
        }

        // 判断是否是ping消息
        if(msg instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(msg.content().retain()));
            return;
        }

        // 本例仅支持文本消息
        if(!(msg instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(String.format("%s frame types not supported",msg.getClass().getName()));
        }

        String request = ((TextWebSocketFrame)msg).text();
        ctx.channel().write(new TextWebSocketFrame(request + ", 欢迎使用Netty Websocket 服务，现在时刻:"
                        +new Date().toString()));
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
        // 如果解析失败 或者请求里边没有 Upgrade:websocket请求头的话就拒绝
        if(!msg.getDecoderResult().isSuccess()
                || !"websocket".equals(msg.headers().get("Upgrade"))){
            sendHttpResponse(ctx,msg,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.BAD_REQUEST));
            return;
        }

        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:8081/websocket", null, false);
        // 握手响应决定是否支持webSocket
         handshaker = handshakerFactory.newHandshaker(msg);
        if(handshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else{
            handshaker.handshake(ctx.channel(),msg);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest msg, DefaultFullHttpResponse res) {
        // 如果不是200的响应
        if(res.getStatus().code() != 200){
            ByteBuf byteBuf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(byteBuf);
            byteBuf.release();
            HttpHeaders.setContentLength(res,res.content().readableBytes());
        }

        ChannelFuture channelFuture = ctx.channel().writeAndFlush(res);

        // 不是长连接就要给关闭 或者连接出了异常
        if(!HttpHeaders.isKeepAlive(msg) || res.getStatus().code() != 200){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }

    }
}
