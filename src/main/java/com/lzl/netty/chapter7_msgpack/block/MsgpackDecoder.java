package com.lzl.netty.chapter7_msgpack.block;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;

import java.util.List;

/**
 * @author lizanle
 * @data 2019/2/24 8:13 PM
 */
public class MsgpackDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode ~~~");
        final byte[] array;
        final int length = in.readableBytes();
        array = new byte[length];
        in.getBytes(in.readerIndex(), array, 0, length);

        MessagePack messagePack = new MessagePack();
        Value read = messagePack.read(array);
        out.add(read);

    }

}
