package com.lzl.netty.chapter12_privateprotocol.codec;

import com.lzl.netty.chapter12_privateprotocol.struct.Header;
import com.lzl.netty.chapter12_privateprotocol.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;


/**
 * @author lizanle
 * @data 2019/2/28 9:47 PM
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    MarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if(frame == null){
            return null;
        }

        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(in.readInt());
        header.setLength(in.readInt());
        header.setSessionID(in.readLong());
        header.setType(in.readByte());
        header.setPriority(in.readByte());

        int size = in.readInt();
        if(size > 0){
            HashMap<String, Object> map = new HashMap<>(size);
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = in.readInt();
                keyArray = new byte[keySize];
                in.readBytes(keyArray);

                key = new String(keyArray,"utf-8");
                map.put(key,marshallingDecoder.decode(in));
            }

            keyArray = null;
            key = null;
            header.setAttachment(map);
        }

        if(in.readableBytes() > 4){
            nettyMessage.setBody(marshallingDecoder.decode(in));
        }
        nettyMessage.setHeader(header);
        return nettyMessage;
    }
}
