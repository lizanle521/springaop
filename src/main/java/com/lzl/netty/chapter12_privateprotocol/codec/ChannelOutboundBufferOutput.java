package com.lzl.netty.chapter12_privateprotocol.codec;



import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author lizanle
 * @data 2019/2/28 9:06 PM
 */
public class ChannelOutboundBufferOutput implements ByteOutput {
    private ByteBuf byteBuf;
    public ChannelOutboundBufferOutput(ByteBuf out) {
        this.byteBuf = out;
    }


    @Override
    public void write(byte[] bytes, int i, int i1) throws IOException {
        byteBuf.writeBytes(bytes,i,i1);
    }

    @Override
    public void write(int i) throws IOException {
        byteBuf.writeInt(i);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        byteBuf.writeBytes(bytes);
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }
}
