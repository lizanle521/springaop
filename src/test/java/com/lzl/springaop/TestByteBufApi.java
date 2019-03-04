package com.lzl.springaop;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
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
}
