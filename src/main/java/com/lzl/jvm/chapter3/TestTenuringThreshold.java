package com.lzl.jvm.chapter3;

/**
 * -Xms20m -Xmx20m -Xmn10m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 */
public class TestTenuringThreshold {
    private static final int _1mb = 1*1024*1024;

    public static void main(String[] args) {
        byte[] all1,all2,all3,all4;
        all1 = new byte[_1mb/4]; // 第一次分配 0.25m 在eden区
        all2 = new byte[4*_1mb]; // 第二次分配 4m 在eden区 ，此时已经占用了6m,接下来是没有空间再分配4m了
        all3 = new byte[4*_1mb]; // 第三次分配4m 在eden区，发现内存不够，进行gc,由于对象 1,2还有引用，无法回收，那么就直接分配到老年代

        all3 = null; // 老年代对象引用置空
        all3 = new byte[4*_1mb]; //再次分配 4m 再eden区，发现内存不够，进行一次gc,由于MaxTenuringThreshold的值为1，对象1,2被提升到老年代
        // 最后内存布局为 eden区新对象 3，老年代对象为 1,2
    }
}
