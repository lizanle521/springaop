package com.lzl.lambada.assert_type;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class AssertTest {
    public List<String> filter(List<String> fruit, Predicate<String> predicate){
        List<String> s = new ArrayList<>();
        for (String s1 : fruit) {
            if(predicate.test(s1)){
               s.add(s1);
            }
        }
        return s;
    }

    public List<String> filter1(List<String> fruit, Predicate<String> predicate){
        List<String> s = new ArrayList<>();
        for (String s1 : fruit) {
            // 对判定条件取反
            if(predicate.negate().test(s1)){
                s.add(s1);
            }
        }
        return s;
    }

    @Test
    public void testAssert(){
        List<String> list = Arrays.asList("香蕉", "菠萝", "哈密瓜");
        List<String> filter = filter(list, s -> s.length() > 2);
        filter.forEach(System.out::println);
    }
}
