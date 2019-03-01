package com.lzl.netty.chapter12_privateprotocol.server;

import com.lzl.netty.chapter12_privateprotocol.struct.Header;
import com.lzl.netty.chapter12_privateprotocol.struct.MessageType;
import com.lzl.netty.chapter12_privateprotocol.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lizanle
 * @data 2019/3/1 5:07 PM
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(HeartBeatRespHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 如果是心跳请求消息，则给个回应
        if(message != null &&
                message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()){
            logger.info("receive client heart beat message:" + message);
            NettyMessage nettyMessage = buildHeartBeatResp();
            ctx.writeAndFlush(nettyMessage);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeartBeatResp() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
