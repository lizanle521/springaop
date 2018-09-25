package com.lzl.lambada.chapter3.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Test
    public void testStream3(){
        List<Integer> integerList = Arrays.asList(1, 3, 5, 7, 9);
        OptionalDouble max = integerList.parallelStream().map(i -> i - 1).mapToDouble(i -> i * 1.0).max();
        System.out.println(max);
    }

    @Test
    public void testStream4(){
        OptionalInt max = IntStream.rangeClosed(0, 10).map(i -> i + 1).max();
        System.out.println(max);

        IntStream intStream = IntStream.rangeClosed(0, 10);
        Stream<Integer> boxed = intStream.boxed();
        System.out.println(boxed);
    }

    @Test
    public void testStream5(){
        Stream<Integer> integerStream = Stream.of(1, 2);
        IntStream intStream = integerStream.mapToInt(Integer::intValue);
    }
}
