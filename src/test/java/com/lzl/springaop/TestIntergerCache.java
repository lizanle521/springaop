package com.lzl.springaop;

import org.junit.Test;

/**
 * Created by Lizanle on 2017/12/18.
 */
public class TestIntergerCache {
    @Test
    public void testIntegerCache(){
        Integer a = 100,b=100;
        System.out.println(a == b);
        Integer c = 10000,d = 10000;
        int f = 10000;
        System.out.println(c == d);
        System.out.println(f == c);
    }
}
