package com.lzl.netty.chapter12_privateprotocol.struct;

/**
 * @author lizanle
 * @data 2019/3/1 3:12 PM
 */
public enum MessageType {
    SERVICE_REQ((byte) 0), SERVICE_RESP((byte) 1), ONE_WAY((byte) 2), LOGIN_REQ(
            (byte) 3), LOGIN_RESP((byte) 4), HEARTBEAT_REQ((byte) 5), HEARTBEAT_RESP(
            (byte) 6);

    private byte value;

    private MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return this.value;
    }
}
