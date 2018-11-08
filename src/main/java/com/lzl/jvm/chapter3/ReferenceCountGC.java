package com.lzl.jvm.chapter3;

import org.junit.Test;

/**
 * -ea -verbose:gc -XX:+PrintGCDetails -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC  -Xloggc:gcc.log
 */
public class ReferenceCountGC {
    public Object instance = null;
    private static final int  _1mb = 1024*1024;
    private byte[] bigSize = new byte[ 1 * _1mb];

    @Test
    public void testGC(){
        ReferenceCountGC objA = new ReferenceCountGC();
        ReferenceCountGC objB = new ReferenceCountGC();
        objA.instance = objA;
        objB.instance = objB;
       //  objA = null;
       //  objB = null;

            //ReferenceCountGC a = null;
           // ReferenceCountGC b = null;
        System.out.println("free before:"+Runtime.getRuntime().freeMemory()/1024/1024);// 167  167

        // 调用 gc，但是 objA 和 objB 会被回收
         System.gc();
         // 通过是否注释   objA = null;
        //         objB = null; 这两行代码来确定，注释了以后，可用内存增加了 2
        System.out.println("free after:"+Runtime.getRuntime().freeMemory()/1024/1024);// 175 177
    }
}
