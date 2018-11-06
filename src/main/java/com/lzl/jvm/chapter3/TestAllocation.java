package com.lzl.jvm.chapter3;

import org.slf4j.Logger;

/**
 * 内存分配，针对年轻代，分配的对象放不下的时候，会发生minor Gc(Allocation Failure)
 * -ea -verbose:gc  -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDateStamps -XX:SurvivorRatio=8
 */
public class TestAllocation {
    private final static int _1mb  = 1024 * 1023;
    public static void main(String[] args) {
        byte[] all1,all2,all3,all4;
        all1 = new byte[2 * 1024 * 1024];
        all2 = new byte[2 * 1024 * 1024];
        //all3 = new byte[2 * 1024 * 1024]; //出现一次minor gc，可能会有所不同，我这里用的idea,运行就占用2-3m，加上分配的4m,实际新生代又只有 9m左右。看我的 heap打印日志
        //all4 = new byte[4 * 1024 * 1024];
    }

    /**
     * Heap
     Disconnected from the target VM, address: '127.0.0.1:59036', transport: 'socket'
     par new generation   total 9216K, used 7220K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     eden space 8192K,  88% used [0x00000000fec00000, 0x00000000ff30d108, 0x00000000ff400000)
     from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     concurrent mark-sweep generation total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     Metaspace       used 3061K, capacity 4568K, committed 4864K, reserved 1056768K
     class space    used 332K, capacity 392K, committed 512K, reserved 1048576K
     */
}
