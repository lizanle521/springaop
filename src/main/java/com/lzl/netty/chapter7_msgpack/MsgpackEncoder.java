package com.lzl.netty.chapter7_msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author lizanle
 * @data 2019/2/22 8:05 PM
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        System.out.println("encode ~~~");
        MessagePack messagePack = new MessagePack();
        // 将对象序列化成流
        byte[] bytes = messagePack.write(msg);
        // 将流写入buffer
        out.writeBytes(bytes);
    }
}
