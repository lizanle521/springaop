package com.lzl.netty.chapter7_msgpack.block;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author lizanle
 * @data 2019/2/24 8:05 PM
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        System.out.println("encode ~~~");
        MessagePack messagePack = new MessagePack();
        // 将对象序列化成流
        byte[] bytes = messagePack.write(msg);
        // 如果断点进去就会发现里边报错了 org.msgpack.MessageTypeException: Cannot find template for class com.lzl.netty.chapter7_msgpack.block.UserInfo class.
        // Try to add @Message annotation to the class or call MessagePack.register(Type).
        System.out.println("after encode bytes:"+bytes.length);
        // 将流写入buffer
        out.writeBytes(bytes);
    }
}
