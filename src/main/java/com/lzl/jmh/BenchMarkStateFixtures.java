package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class BenchMarkStateFixtures {
    double x;

    /**
     * 由于State状态对象在benchmark运行的整个生命周期内都被引用，
     * 有利于用一些方法去管理state状态对象，通常我们称作修正方法。通常我们常见的有 Junit 和 TestNG
     * 修正方法只对 State状态对象有效，否则jmh将会编译失败
     */
    @Setup
    public void set(){
        x = Math.PI;
    }

    /**
     * 检查benchmark的状态对象
     */

    @TearDown
    public void check() {
        assert x > Math.PI : " nothing changed?";
    }

    @Benchmark
    public void measureRight(){
        x ++;
    }

    /**
     * 然而这个方法将会check()失败，是在最后的时候才会执行
     */
    @Benchmark
    public void measureWrong(){
        double x=0;
        x++;
    }

    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder()
                .include(BenchMarkStateFixtures.class.getSimpleName())
                .forks(1)
                .jvmArgs("-ea") // 开启断言
                .build();
        new Runner(op).run();
    }

}
