package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BenchMarkFork {
    /**
     * jvm 天生就能够进行性能优化，这对于benchmark是一个坏消息。
     * 因为不同的测试能够混淆他们的测试结果
     * 并且他会渲染一个不规则的坏的代码。fork（就是让代码跑在独立的进程里）能够避免这些问题
     * jmh默认会对测试进行fork
     */

    public interface Counter {
        int inc();
    }

    public class Counter1 implements Counter {
        private int x;
        @Override
        public int inc() {
            return x++;
        }
    }

    public class Counter2 implements Counter {
        private int x;
        @Override
        public int inc() {
            return x++;
        }
    }

    public int measure(Counter c){
        int s = 0;
        for (int i = 0; i < 10; i++) {
            s += (c.inc());
        }
        return s;
    }

    Counter c1 = new Counter1();
    Counter c2 = new Counter2();

    /**
     * 我们让这个方法跑在同一个jvm上
     * @return
     */
    @Benchmark
    @Fork(0)
    public int measure_1_c1(){
        return measure(c1);
    }

    @Benchmark
    @Fork(0)
    public int measure_2_c2(){
        return measure(c2);
    }

    @Benchmark
    @Fork(0)
    public int measure_3_c1_again(){
        return measure(c1);
    }

    @Benchmark
    @Fork(1)
    public int measure_4_fork_c1(){
        return measure(c1);
    }

    @Benchmark
    @Fork(1)
    public int measure_5_fork_c2(){
        return measure(c2);
    }

    @Test
    public void test() throws RunnerException {
        Options build = new OptionsBuilder()
                .include(BenchMarkFork.class.getSimpleName())
                .build();
        new Runner(build).run();
    }


}
