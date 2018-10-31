package com.lzl.lambada.assert_type;

import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
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

    /**
     *  参数验证场景
     * @param t
     * @param map
     * @param <T>
     */
    public static <T> void checkParams(T t,Map<String,Predicate<T>> map){
        if(CollectionUtils.isEmpty(map) == false){
            map.entrySet().forEach(r->{
                if(r.getValue().test(t)){
                    throw new RuntimeException(r.getKey());
                }
            });
        }
    }

    @Test
    public void testCheckParams(){
        Map<String,Predicate<TestVo>> map = new HashMap<>();
        map.put("价格不能小于0",r->r.getPrice()<0);
        map.put("名字不能为空",r-> StringUtils.isEmpty(r.getName()));
        checkParams(new TestVo(),map);
    }



    @Test
    public void testAssert(){
        List<String> list = Arrays.asList("香蕉", "菠萝", "哈密瓜");
        List<String> filter = filter(list, s -> s.length() > 2);
        filter.forEach(System.out::println);
    }

    @Test
    public void runnalbeAndLambda(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("111");
            }
        }).start();
        new Thread(()->{
            System.out.println(111);
        }).start();
    }

    class TestVo{
        private int price;
        private String name;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
