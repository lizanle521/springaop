package com.lzl.lambada.chapter4;


import org.junit.Test;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * 自定义收集器
 */
public class CollectTest {
    /**
     * 在x轴上的有序的点集，
     * 如果两个点的距离小于等于2，则点集可以归位一个段
     * 否则是新的一个段
     * @param sortedPointList
     * @return
     */
    public Deque<Deque<Point>> groupByProximity(java.util.List<Point> sortedPointList){
        Deque<Deque<Point>> points = new ArrayDeque<>();
        points.add(new ArrayDeque<>());
        for (Point point : sortedPointList) {
            Deque<Point> last = points.getLast();
            if(!last.isEmpty() && last.getLast().distance(point) > 2){
                ArrayDeque<Point> newSegment = new ArrayDeque<>();
                newSegment.add(point);
                points.add(newSegment);
            }
            else{
                last.add(point);
            }
        }
        return points;
    }

    /**
     * 用lambda表达式重写groupByProximity方法的逻辑
     * 收集器有3个组件：
     * 提供器: 用作容器的构造器
     * 积聚器： 将一个point添加到部分解决方案中
     * 组合器: 将部分解决方案合并到一起
     */
    public  Deque<Deque<Point>> testGroupByProximityWriteByLambda(java.util.List<Point> sortedPointList){
        // 提供器
        Supplier<Deque<Deque<Point>>> supplier = ()->{
            Deque<Deque<Point>> points = new ArrayDeque<>();
            points.add(new ArrayDeque<>());
            return points;
        };
        // 积聚器
        BiConsumer<Deque<Deque<Point>>,Point> accumlator = (ddp,p)->{
            Deque<Point> last = ddp.getLast();
            if(!last.isEmpty() && last.getLast().distance(p) > 2){
                Deque<Point> newSegment = new ArrayDeque<>();
                newSegment.add(p);
                ddp.add(newSegment);
            }else{
                last.add(p);
            }
        };
        // 组合器
        BinaryOperator<Deque<Deque<Point>>> combiner = (left,right)->{
            Deque<Point> last = left.getLast();
            if(last.isEmpty()) {
                return right;
            }
            Deque<Point> first = right.getFirst();
            if(first.isEmpty()){
                return left;
            }
            Point first1 = first.getFirst();
            if(last.getLast().distance(first1) <= 2){
                last.addAll(first);
                right.removeFirst();
            }
            left.addAll(right);
            return left;
        };

        Deque<Deque<Point>> collect = sortedPointList.stream().collect(Collector.of(supplier, accumlator, combiner));
        return collect;
    }

    @Test
    public void test(){
        java.util.List<Point> list = new ArrayList<>();
        list.add(new Point(3,0));
        list.add(new Point(6,0));
        list.add(new Point(8,0));
        list.add(new Point(10,0));
        list.add(new Point(14,0));
        Deque<Deque<Point>> deques = groupByProximity(list);
        Deque<Deque<Point>> deques1 = testGroupByProximityWriteByLambda(list);
        System.out.println(deques);
        System.out.println(deques1);

    }

    /**
     * 原生值的汇聚
     */
    @Test
    public void sum(){
        IntStream intStream = IntStream.of(1, 2, 3);
        int sum = intStream.sum();
        System.out.println(sum);
    }


    /**
     * 原生流的汇聚
     */
    @Test
    public void reduce(){
        int reduce = IntStream.of(1, 2, 3).reduce(0, (a, b) -> a + b);
        System.out.println(reduce);

        OptionalInt reduce1 = IntStream.of(1, 2, 3).reduce((a, b) -> a + b);
        System.out.println(reduce1);

        /**
         * 计算阶乘
         */
        int reduce2 = IntStream.rangeClosed(1, 10).reduce(1, (a, b) -> a * b);
        System.out.println(reduce2);
    }


}
