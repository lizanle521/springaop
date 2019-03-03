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
        System.out.println("NettyMessageDecoder decode start");
        // 通过父类的decode方法进行拆包，后续都要针对这个拆好的包进行操作
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if(frame == null){
            return null;
        }

        NettyMessage nettyMessage = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());         header.setPriority(frame.readByte());

        int size = frame.readInt();
        if(size > 0){
            HashMap<String, Object> map = new HashMap<>(size);
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);

                key = new String(keyArray,"utf-8");
                map.put(key,marshallingDecoder.decode(frame));
            }

            keyArray = null;
            key = null;
            header.setAttachment(map);
        }

        if(frame.readableBytes() > 4){
            nettyMessage.setBody(marshallingDecoder.decode(frame));
        }
        nettyMessage.setHeader(header);
        return nettyMessage;
    }
}
