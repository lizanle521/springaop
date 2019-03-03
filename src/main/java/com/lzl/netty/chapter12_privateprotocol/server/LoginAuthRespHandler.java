package com.lzl.netty.chapter12_privateprotocol.server;

import com.lzl.netty.chapter12_privateprotocol.struct.Header;
import com.lzl.netty.chapter12_privateprotocol.struct.MessageType;
import com.lzl.netty.chapter12_privateprotocol.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizanle
 * @data 2019/3/1 3:28 PM
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {
    private Map<String,Boolean> nodeCheck = new ConcurrentHashMap<>();

    private String[] whiteList = {"127.0.0.1"};


    /**
     * codec解析完消息以后 交给channelhandler处理。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("LoginAuthRespHandler channelRead start");
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手请求消息，处理，其他消息透传
        if(message != null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()){
            String host = ctx.channel().remoteAddress().toString();
            NettyMessage resp = null;
            // 重复登陆，拒绝
            if(nodeCheck.containsKey(host)){
                System.out.println("repeat login ,illegal!");
                resp = buildResponse((byte) -1);
                ctx.writeAndFlush(resp);
            }else{
                InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
                String ip = socketAddress.getAddress().getHostAddress();
                boolean isOk = false;
                for (String s : whiteList) {
                    if(s.equals(ip))
                    {
                        isOk = true;
                        nodeCheck.put(host,true);
                        break;
                    }
                }
                resp = isOk ? buildResponse((byte)1) : buildResponse((byte)-1);
                System.out.println("the login response is:"+resp);
                ctx.writeAndFlush(resp);
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常关闭连接
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        cause.printStackTrace();
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setSessionID(2L);
        header.setCrcCode(222);
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }
}
