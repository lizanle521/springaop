package com.lzl.jvm.chapter2;

import java.util.ArrayList;

/**
 * -Xmx2m -Xms2m
 * -XX:-UseGCOverheadLimit
 */
public class ConstantPoolOOM {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(String.valueOf(i+"---------").intern());
        }
    }
}
