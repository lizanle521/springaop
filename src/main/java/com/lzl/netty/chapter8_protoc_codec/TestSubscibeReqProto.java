package com.lzl.netty.chapter8_protoc_codec;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizanle
 * @Date 2019/2/25 11:00
 */
public class TestSubscibeReqProto {

    private static byte[] encode(SubscribeReqProto.SubscribeReq reqProto){
        return reqProto.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubReqProto(){
        SubscribeReqProto.SubscribeReq.Builder builder =
                SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("lizanle");
        builder.setProductName("netty book");
        List<String> address = new ArrayList<String>();
        address.add("Nanjing");
        address.add("Beijing");
        builder.addAllAddress(address);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq subReqProto = createSubReqProto();
        System.out.println("before encode:" + subReqProto.toString());

        SubscribeReqProto.SubscribeReq decode = decode(encode(subReqProto));
        System.out.println("after encode:" + decode.toString());

        System.out.println("assert eq:"+decode.equals(subReqProto));
    }
}
