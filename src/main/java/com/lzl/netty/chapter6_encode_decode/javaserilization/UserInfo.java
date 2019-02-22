package com.lzl.netty.chapter6_encode_decode.javaserilization;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author lizanle
 * @Date 2019/2/22 10:25
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 2669960651014197594L;
    private String userId;

    private String userName;

    private String address;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] codec(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] userIdBytes = this.userId.getBytes();
        byte[] userNameBytes = this.userName.getBytes();
        byte[] addressBytes = this.address.getBytes();

        buffer.putInt(userIdBytes.length);
        buffer.put(userIdBytes);

        buffer.putInt(userNameBytes.length);
        buffer.put(userNameBytes);

        buffer.putInt(addressBytes.length);
        buffer.put(addressBytes);

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        return bytes;
    }

    public byte[] codec(ByteBuffer buffer){
        buffer.clear();
        byte[] userIdBytes = this.userId.getBytes();
        byte[] userNameBytes = this.userName.getBytes();
        byte[] addressBytes = this.address.getBytes();

        buffer.putInt(userIdBytes.length);
        buffer.put(userIdBytes);

        buffer.putInt(userNameBytes.length);
        buffer.put(userNameBytes);

        buffer.putInt(addressBytes.length);
        buffer.put(addressBytes);

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        return bytes;
    }
}
