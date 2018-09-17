package com.lzl.lambada.consumer_type;

import org.junit.Test;

import java.util.function.Consumer;

public class ConsumerTypeTest {

    public void donation(Integer money, Consumer<Integer> consumer){
        consumer.andThen(a->{
            System.out.println(a);
        }).accept(money);
    }

    @Test
    public void testConsumer(){
        donation(199,money->{
            System.out.println("我捐赠了"+money+"元");
        });
    }
}
