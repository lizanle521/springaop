package com.lzl.netty.chapter7_msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;

import java.util.List;

/**
 * @author lizanle
 * @data 2019/2/22 8:13 PM
 */
public class MsgpackDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode ~~~");
        final byte[] array;
        final int length = in.readableBytes();
        array = new byte[length];
        // 因为这个方法不会修改 readerIndex,但是后边messagepack的解码却让out多了一些信息，所以netty不允许这么做，会出现
        // MsgpackDecoder.decode did not read anything  but decode a message 错误
        in.getBytes(in.readerIndex(), array, 0, length);

        MessagePack messagePack = new MessagePack();
        Value read = messagePack.read(array);
        if(read != null && read.isNilValue() == false) {
            out.add(read);
        }

        in.skipBytes(in.readableBytes());
    }
    /**
     * 为什么netty不允许这么做呢？
     * ByteToMessageDecoder 中的  callDecode方法可以看出来
     * 在执行decode之前会记录 out in.readablebytes等大小。
     * 如果 执行完毕，out变了大小，而in.readablebytes大小没变，那么就
     * 可能会造成无限循环：每次都传入 byteBuf，都有readablebytes,而且没有改变。但是out却会每次都增加一个对象。
     */
}
