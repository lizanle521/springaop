package com.lzl.lambada.chapter2;

import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class LambdaUseMethodAndConstructor {
    @Test
    public void test(){
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(2);
        list.add(3);
        list.forEach(s-> System.out.println(s));
        list.forEach(System.out::println);

        Integer[] integers = new Integer[list.size()];

        Arrays.sort(list.toArray(integers),Integer::compareUnsigned);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
    }

    @Test
    public void testMapReplace(){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("alpha","x");
        map.put("bravo","y");
        map.put("charlie","z");

        String str = "alpha-bravo-charlie";
        // 相当于执行3次 str.replace("alpha","x");
        // str.replace("bravo","y");
        //  str.replace("charlie","z");
        map.replaceAll(str::replaceAll);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey()+"="+entry.getValue());
        }


    }

    @Test
    public void testMapConcat(){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("alpha","x");
        map.put("bravo","y");
        map.put("charlie","z");

        map.replaceAll(String::concat);
        System.out.println(map.toString());
    }

    @Test
    public void testConstructor(){
        Stream<String> stream = Stream.of("","","");
        Stream<File> rStream = stream.map(File::new);

        /**
         * 这里没有返回值的情况 lambda体应该是 表达式，而不能是值
         */
        Consumer<Integer> d = ( i ) -> i++;
    }
}
