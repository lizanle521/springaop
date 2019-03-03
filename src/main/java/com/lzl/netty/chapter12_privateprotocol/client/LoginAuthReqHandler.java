package com.lzl.netty.chapter12_privateprotocol.client;

import com.lzl.netty.chapter12_privateprotocol.struct.Header;
import com.lzl.netty.chapter12_privateprotocol.struct.MessageType;
import com.lzl.netty.chapter12_privateprotocol.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 在通道激活时发起请求
 * @author lizanle
 * @data 2019/3/1 3:06 PM
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("LoginAuthReqHandler channelread start");
        NettyMessage nettyMessage = (NettyMessage) msg;

        //如果是握手应答消息，需要判断是否认证成功
        if(nettyMessage.getHeader() != null
                && nettyMessage.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            byte loginResult = (byte) nettyMessage.getBody();
            if(loginResult != (byte)1){
                // 握手失败，连接关闭
                ctx.close();
            }else{
                System.out.println("login is ok:"+nettyMessage);
                ctx.fireChannelRead(msg); // 处理完了继续让下一个handler继续处理
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setPriority((byte)1);
        header.setSessionID(1L);
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }
}
