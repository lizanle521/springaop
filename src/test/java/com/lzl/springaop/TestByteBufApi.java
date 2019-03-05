package com.lzl.springaop;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.junit.Test;

/**
 * @author lizanle
 * @data 2019/3/4 10:28 PM
 */
public class TestByteBufApi {

    @Test
    public void readwriteboolean(){
         ByteBuf byteBuf;
        byteBuf = PooledByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBoolean(true);
        assert byteBuf.writerIndex() == 1;
        assert byteBuf.readerIndex() == 0;
        assert byteBuf.capacity() == 256;
        boolean b = byteBuf.readBoolean();
        assert b == true;
        assert byteBuf.readerIndex() == 1;
    }

    @Test
    public void readwritebyte(){
        ByteBuf byteBuf;
        byteBuf = PooledByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeByte(1);
        assert byteBuf.writerIndex() == 1;
        assert byteBuf.readerIndex() == 0;
        assert  byteBuf.capacity() == 256;
        byte b = byteBuf.readByte();
        assert  b == 1;
        assert byteBuf.readerIndex() == 1;
    }

    @Test
    public void readwriteUnsignedByte(){
        ByteBuf byteBuf;
        byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeShort(1);
        assert  byteBuf.writerIndex() == 2;
        assert byteBuf.readerIndex() == 0;
        assert byteBuf.capacity() == 256;
        int unsignedShort = byteBuf.readUnsignedShort();
        assert unsignedShort == 1;
        assert byteBuf.readerIndex() == 2;
    }

    @Test
    public void readwriteMedium(){
        ByteBuf byteBuf;
        byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeMedium(1);

        assert byteBuf.writerIndex() == 3;
        assert byteBuf.readerIndex() == 0;
        assert byteBuf.capacity() == 256;
        int i = byteBuf.readMedium();
        assert byteBuf.readerIndex() == 3;
        assert byteBuf.readableBytes() == 0;
        assert i == 1;
    }

    @Test
    public void readwriteInt(){
        ByteBuf byteBuf;
        byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeInt(1);
        assert byteBuf.writerIndex() == 4;
        assert byteBuf.readerIndex() == 0;
        assert byteBuf.capacity() == 256;
        int i = byteBuf.readInt();
        assert i == 1;
        assert byteBuf.readerIndex() == 4;
    }

    @Test
    public void readwriteBytes(){
        ByteBuf byteBuf;
        byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();

        ByteBuf bf;
        bf = UnpooledByteBufAllocator.DEFAULT.buffer();
        bf.writeInt(1);
        ///ByteBuf.writeBytes(ByteBuf bf) 将源bf中所有刻度字节写入到当前bytebuffer.操作成功以后，当前bytebuf的writeindex = writerindex + bf.readablebytes;
        byteBuf.writeBytes(bf);

        assert byteBuf.writerIndex() == 4;
        assert byteBuf.readerIndex() == 0;
        assert bf.readerIndex() == 4;

        int i = byteBuf.readInt();
        assert  i == 1;
        assert  byteBuf.readerIndex() == 4;


        //ByteBuf.writeBytes(ByteBuf src,int length) 将源ByteBuf中的可读字节写入到当前bytebuf中，写入的字节长度数为length。
        // 操作成功以后，当前bytebuf 的writeIndex = writeIndex + length;
        bf.writeLong(8L);
        assert bf.writerIndex() == 12;
        byteBuf.writeBytes(bf,8);
        assert byteBuf.writerIndex() == 12;
        long l = byteBuf.readLong();
        assert l == 8L;
        assert byteBuf.readerIndex() == 12;
        assert byteBuf.readableBytes() == 0;
        assert  byteBuf.writableBytes() == 256-12;


        //ByteBuf.writeBytes(ByteBuf src,int srcIndex,int length),将源ByteBuf中的可读字节写入到当前bytebuf中，写入的字节长度为
        // length,起始索引为srcIndex
        bf.writeInt(2);
        bf.writeInt(2);
        assert bf.writerIndex() == 20;

        byteBuf.writeBytes(bf,12,8);

        assert byteBuf.writerIndex() == 20;
        int a = byteBuf.readInt();
        assert  byteBuf.readerIndex() == 16;
        int b = byteBuf.readInt();
        assert byteBuf.readerIndex() == 20;
        assert a == 2;
        assert b == 2;

        // ByteBuf.writeBytes(byte[] src) ，将源字节数组src的所有字节写入到当前bytebuf中，
        String s = "hello lizanle";
        byte[] bytes = s.getBytes();
        int length = bytes.length;
        byteBuf.writeBytes(bytes);

        assert byteBuf.writerIndex() == 20 + length;
        byte[] sbt = new byte[length];
        byteBuf.readBytes(sbt,0,length);
        String s1 = new String(sbt);
        assert s1.equals(s);

    }
}
