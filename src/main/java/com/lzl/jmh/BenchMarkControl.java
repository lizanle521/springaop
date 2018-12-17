package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Control;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.atomic.AtomicBoolean;

@State(Scope.Group)
public class BenchMarkControl {
    public final AtomicBoolean flg = new AtomicBoolean();

    @Benchmark
    @Group("pingpong")
    public void ping(Control control){
        while ((!control.stopMeasurement) && flg.compareAndSet(true,false)){
            System.out.println(Thread.currentThread().getName()+":ping");
            System.out.println(Thread.currentThread().getName()+":"+flg.get());
        }
    }

    @Benchmark
    @Group("pingpong")
    public void pong(Control control){
        while ((!control.stopMeasurement) && flg.compareAndSet(false,true)){
            System.out.println(Thread.currentThread().getName()+":pong");
            System.out.println(Thread.currentThread().getName()+":"+flg.get());
        }
    }

    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder()
                .include(BenchMarkControl.class.getSimpleName())
                .threads(2)
                .forks(1)
                .build();
        new Runner(op).run();
    }

    /**
     * 这里的结果并不是 ping pong ping pong。
     * 因为输出的时候并不是同步的。
     * 但是可以肯定的是  pong 先执行。并且 pingpong之间的交替打印 不会很乱。总体有序。
     */

}
