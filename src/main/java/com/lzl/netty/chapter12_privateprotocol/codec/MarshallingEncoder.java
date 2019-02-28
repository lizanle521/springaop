package com.lzl.netty.chapter12_privateprotocol.codec;

import com.lzl.netty.chapter9_marshalling.MarshallingCodedFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;


/**
 * @author lizanle
 * @data 2019/2/28 8:58 PM
 */
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLAEHOLDER = new byte[4];

    Marshaller marshaller;

    public MarshallingEncoder() {
        this.marshaller = MarshallingCodedFactory.buildMarshalling();
    }

    protected void encode(Object msg, ByteBuf out) throws Exception{
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLAEHOLDER);

            // marshaller写 消息体
            ChannelOutboundBufferOutput channelOutboundBuffer = new ChannelOutboundBufferOutput(out);
            marshaller.start(channelOutboundBuffer);
            marshaller.writeObject(msg);
            marshaller.finish();

            // 重写长度
            out.setInt(lengthPos,out.writerIndex()-lengthPos-4);
        } finally {
            marshaller.close();
        }
    }
}
