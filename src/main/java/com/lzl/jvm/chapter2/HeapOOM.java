package com.lzl.jvm.chapter2;

import org.junit.Test;

import java.util.ArrayList;

/**
 * 堆溢出实验
 * jvm args: -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
    static class OOMObject{

    }

    @Test
    public void test(){
        ArrayList<OOMObject> oomObjects = new ArrayList<>();
        while (true){
            oomObjects.add(new OOMObject());
        }
    }
}
