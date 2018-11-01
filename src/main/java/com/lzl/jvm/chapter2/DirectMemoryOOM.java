package com.lzl.jvm.chapter2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * -Xmx20m -XX:MaxDirectMemorySize=10m
 */
public class DirectMemoryOOM {
    private static final int _1M = 1024 * 1024 * 1;
    public static void main(String[] args) throws IllegalAccessException {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe o = (Unsafe)field.get(null);
        while (true){
            o.allocateMemory(_1M);
        }
    }
}
