package com.lzl.netty.chapter12_privateprotocol.client;

import com.lzl.netty.chapter12_privateprotocol.struct.Header;
import com.lzl.netty.chapter12_privateprotocol.struct.MessageType;
import com.lzl.netty.chapter12_privateprotocol.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 客户端主动发送心跳消息。
 * @author lizanle
 * @data 2019/3/1 4:28 PM
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter {

     private final static Logger logger  = LoggerFactory.getLogger(HeartBeatReqHandler.class);

     private volatile ScheduledFuture<?> hearBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 发现握手成功，主动发送心跳消息
        if(message != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            // 每5秒执行一次heartbeat
            hearBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx),0,5000, TimeUnit.MILLISECONDS);
        }else if(message != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()){
            logger.info("client received server heart beat message");
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(hearBeat != null){
            hearBeat.cancel(true);
            hearBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }

    private class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage message = buildHeartBeat();
            logger.info("client send heart beat message to server:" + message);
            ctx.writeAndFlush(message);
        }

        private NettyMessage buildHeartBeat() {
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);

            return message;
        }


    }
}
