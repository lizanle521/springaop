package com.lzl.netty.chapter12_privateprotocol.codec;

import com.lzl.netty.chapter12_privateprotocol.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.Map;

/**
 * 将对象写入缓冲区
 * @author lizanle
 * @data 2019/2/28 8:56 PM
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() {
        this.marshallingEncoder = new MarshallingEncoder();
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        System.out.println("NettyMessageEncoder encode start");
        if(msg == null || msg.getHeader() == null){
            throw new Exception("The encode message or message header is null.pls check it");
        }
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(msg.getHeader().getCrcCode());
        buffer.writeInt(msg.getHeader().getLength());
        buffer.writeLong(msg.getHeader().getSessionID());
        buffer.writeByte(msg.getHeader().getType());
        buffer.writeByte(msg.getHeader().getPriority());
        buffer.writeInt(msg.getHeader().getAttachment().size());

        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> entry : msg.getHeader().getAttachment().entrySet()) {
            key = entry.getKey();
            keyArray = key.getBytes("utf-8");
            buffer.writeInt(keyArray.length);
            buffer.writeBytes(keyArray);

            value = entry.getValue();
            marshallingEncoder.encode(value,buffer);
        }

        key = null;
        keyArray = null;
        value = null;

        if(msg.getBody() != null){
            marshallingEncoder.encode(msg.getBody(),buffer);
        }else{
            buffer.writeInt(0);
        }

        // 之前写了crcCode 4bytes，除去crcCode和length 8bytes即为更新之后的字节
        buffer.setInt(4,buffer.readableBytes()-8);

        out.add(buffer);
    }
}
