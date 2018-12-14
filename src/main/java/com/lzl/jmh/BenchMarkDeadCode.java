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
public class BenchMarkDeadCode {
    /**
     * 防止代码被优化掉，被测试的方法最好返回一个结果
     */
    private double x = Math.PI;

    @Benchmark
    public void baseLine(){
        // do nothing
    }

    /**
     * 这个方法的调用 和 baseLine的方法调用时间一样，说明 Math.log方法调用被优化掉了
     */
    @Benchmark
    public void measureWrong(){
        Math.log(x);
    }

    @Benchmark
    public double measureRight(){
        return Math.log(x);
    }

    @Test
    public void test() throws RunnerException {
        Options build = new OptionsBuilder()
                .include(BenchMarkDeadCode.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(build).run();
    }
}
