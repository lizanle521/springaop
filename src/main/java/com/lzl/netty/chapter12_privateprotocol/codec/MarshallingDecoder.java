package com.lzl.netty.chapter12_privateprotocol.codec;

import com.lzl.netty.chapter9_marshalling.MarshallingCodedFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Unmarshaller;

/**
 * @author lizanle
 * @data 2019/2/28 10:29 PM
 */
public class MarshallingDecoder {
    private final Unmarshaller unmarshaller;

    public MarshallingDecoder() {
        this.unmarshaller = MarshallingCodedFactory.buildUnMarshalling();
    }


    protected Object decode(ByteBuf in) throws Exception {
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        ChannelBufferByteInput byteInput = new ChannelBufferByteInput(buf);

        Object o;
        try {
            unmarshaller.start(byteInput);
            o = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex()+objectSize);
        } finally {
            unmarshaller.close();
        }

        return o;
    }
}
