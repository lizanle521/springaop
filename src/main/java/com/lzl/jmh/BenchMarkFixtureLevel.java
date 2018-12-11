package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class BenchMarkFixtureLevel {
    double x;

    /**
     * Level.Trial: 整个benchmark运行的前后 进行的 check . before or after the entire benchmark run (the sequence of iterations)，会在最后的迭代进行的时候运行
     * Level.Iteration: benchmark 每个迭代前后 before or after the benchmark iteration (the sequence of invocations),将不会有迭代结果出现。
     * Level.Invocation; benchmark的方法调用前后  before or after the benchmark method invocation (WARNING: read the Javadoc before using)，将会在每个方法的调用前后
     */
    @TearDown(Level.Invocation)
    public void check(){
        assert  x > Math.PI : "nothing changed?";
    }

    @Benchmark
    public void measureRight(){
        x ++;
    }

    @Benchmark
    public void measureWrong(){
        double x = 0;
        x++;
    }

    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder()
                .include(BenchMarkFixtureLevel.class.getSimpleName())
                .jvmArgs("-ea")
                .shouldFailOnError(false)
                .forks(1)
                .build();
        new Runner(op).run();
    }
}
