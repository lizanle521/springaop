package com.lzl.lambada.chapter3.stream;

import org.junit.Test;

import java.util.stream.IntStream;

public class StreamTest {
    @Test
    public void testStream1(){
        IntStream.iterate(1,i->i*2).limit(10).forEachOrdered(System.out::println);
    }

    @Test
    public void testStream2(){
        IntStream limit = IntStream.iterate(1, i -> i * 2).limit(10);
        limit.parallel().forEach(System.out::println);
    }
}
