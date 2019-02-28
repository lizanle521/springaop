package com.lzl.netty.chapter12_privateprotocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

/**
 * @author lizanle
 * @data 2019/2/28 10:41 PM
 */
public class ChannelBufferByteInput implements ByteInput {

    private final ByteBuf byteBuf;

    public ChannelBufferByteInput(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public int read() throws IOException {
        if(byteBuf.isReadable()){
            return byteBuf.readByte() & 0xff;
        }
        return -1;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return read(bytes,0,bytes.length);
    }

    /**
     * 如果可读取的字节不够读取长度，那么读取长度就变为可读取的字节。
     * 譬如原定要读 20个字节。但是可读字节只有10个字节。那么就读10个字节
     * @param bytes 字节数组
     * @param dsIndex 从byteBuf的开始位置，
     * @param length 读取的长度
     * @return
     * @throws IOException
     */
    @Override
    public int read(byte[] bytes, int dsIndex, int length) throws IOException {
        int available = available();
        if(available == 0){
            return -1;
        }
        length = Math.min(available, length);
        byteBuf.readBytes(bytes,dsIndex,length);
        return length;
    }

    /**
     * 可读字节的长度
     * @return
     * @throws IOException
     */
    @Override
    public int available() throws IOException {
        return byteBuf.readableBytes();
    }

    /**
     * 如果跳过的字节 大于可读的字节，那么跳过的字节就是可读字节的长度
     * @param l 跳过的字节
     * @return
     * @throws IOException
     */
    @Override
    public long skip(long l) throws IOException {
        int readableBytes = byteBuf.readableBytes();
        if(readableBytes < l){
            l = readableBytes;
        }
        byteBuf.readerIndex((int)(byteBuf.readerIndex()+l));
        return l;
    }

    @Override
    public void close() throws IOException {

    }
}
