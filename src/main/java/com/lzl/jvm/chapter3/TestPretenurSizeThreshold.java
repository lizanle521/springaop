package com.lzl.jvm.chapter3;

/**
 * -verbose:gc  -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDateStamps -XX:SurvivorRatio=8
 * -XX:PretenureSiseThreshol=3000000
 */
public class TestPretenurSizeThreshold {
    public static void main(String[] args) {
        byte[] all = new byte[4*1024*1024]; // 根据 PretenureSizeThreshold参数，超过3000000字节的对象都分配在老年代
    }

    /**
     *
     * Heap
     par new generation   total 9216K, used 2686K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     eden space 8192K,  32% used [0x00000000fec00000, 0x00000000fee9f970, 0x00000000ff400000)
     from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     concurrent mark-sweep generation total 10240K, used 4096K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     Metaspace       used 3273K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 357K, capacity 388K, committed 512K, reserved 1048576K
     */
}
