package com.lzl.jvm.chapter3;

/**
 * -Xms20m -Xmx20m -Xmn10m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 */
public class TestTenuringThreshold {
    private static final int _1mb = 1*1024*1024;

    public static void main(String[] args) {
        byte[] all1,all2,all3,all4;
        all1 = new byte[_1mb];
        all2 = new byte[4*_1mb];
        //all3 = new byte[4*_1mb];

        //all3 = null;
        //all3 = new byte[4*_1mb];
    }
}
