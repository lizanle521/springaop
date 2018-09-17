package com.lzl.lambada.function_type;

import org.junit.Test;

import java.util.function.Function;

public class FunctionTest {
    public Integer paser(String str, Function<String,Integer> function){
        return function.apply(str);
    }

    public Integer paser1(String str, Function<String,Integer> function){
        return function.andThen(x->x+10).apply(str);
    }

    public Integer paser2(String str, Function<String,Integer> function){
        return function.compose(x->{return  "3";}).andThen(x->x+10).apply(str);
    }

    @Test
    public void testFunction(){
        Integer paser = paser1("120", (s) -> Integer.parseInt(s));
        System.out.println(paser);
    }

    @Test
    public void testCompose(){
        Function<Integer,Integer> times = x -> x * 2;
        Function<Integer,Integer> squre = x -> x * x;
        System.out.println(times.apply(2));
        System.out.println(squre.apply(2));

        // 8  先平方再 apply（apply代表执行调用者）
        System.out.println(times.compose(squre).apply(2));
        // 16 先apply 再平方
        System.out.println(times.andThen(squre).apply(2));
        // 先 平方 再 apply 再平方 =  （2^2*2）^2 = 64
        System.out.println(times.compose(squre).andThen(squre).apply(2));

    }
}
