package com.lzl.jvm.chapter3;

/*
-Xms20m -Xmx20m -Xmn10m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails
-XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
-XX:+HeapDumpOnOutOfMemoryError

promotion failed是在进行Minor GC时，survivor space放不下、对象只能放入旧生代，而此时旧生代也放不下造成的；
concurrent mode failure是在执行CMS GC的过程中同时有对象要放入旧生代，而此时旧生代空间不足造成的。
 */
public class TestTenuringThreshold2 {
    private static final int _1mb = 1*1024*1024;

    public static void main(String[] args) {
        byte[] all1,all2,all3,all4;
        all1 = new byte[8*_1mb];
        all2 = new byte[4*_1mb];
        //all3 = new byte[4*_1mb]; // 放开这个注释就会出现 promotion faild
    }

}
