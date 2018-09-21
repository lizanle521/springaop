package com.lzl.lambada.chapter2;

import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * 使用lambda
 */
public class Test {
    @org.junit.Test
    public void test1(){
        IntPredicate intPredicate = i  -> i > 0 ;

        Comparator<String> cc = (s1,s2)->s1.compareTo(s2);

        IntBinaryOperator[] caculate = new IntBinaryOperator[]{
                (x,y) -> x+y,(x,y)-> x-y,(x,y)->x*y,(x,y)->x/y
        };

        Callable<Runnable> a = () -> ()->{
            System.out.println("aa");
        };

        Callable<Integer> b = true ? (()->23) : (()->24);

        Object c = (Supplier<String>)()->"hi";

        Object d = (Supplier<String>)()->"he";
    }

    public Runnable returnDatePrinter(){
        return ()->{
            System.out.println(new Date());
        };
    }

    public Callable<Runnable> returnCallableRunnable(){
        return ()->()->{
            System.out.println("aaa");
        };
    }
}
