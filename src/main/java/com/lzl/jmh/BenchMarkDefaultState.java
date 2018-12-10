package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 可以在类上边标记 ，只用一个状态
 */
@State(Scope.Thread)
public class BenchMarkDefaultState {
    double x = Math.PI;

    @Benchmark
    public void measure(){
        x++;
    }

    @Test
    public void test() throws Exception{
        Options op = new OptionsBuilder()
                .include(BenchMarkDefaultState.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(op).run();
    }
}
