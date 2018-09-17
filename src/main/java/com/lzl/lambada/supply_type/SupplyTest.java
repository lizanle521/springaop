package com.lzl.lambada.supply_type;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SupplyTest {
    public List<Integer> supply(Integer num, Supplier<Integer> supplier){
        List<Integer> list = new ArrayList<>();
        for (Integer i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }
    @Test
    public void testSupply(){
        List<Integer> supply = supply(10, () -> (int) (Math.random() * 100));
        supply.forEach(System.out::println);
    }
}
