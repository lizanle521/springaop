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
public class BenchMarkConstantFold {
    /**
     * 死代码移除的负面效应就是 常量折叠
     * 如果jvm认识到无论怎么样 计算结果都是一样的话，他能够聪明的进行优化
     * 在我们这个例子中，意味着可以将计算从内部迭代中移除到外边来
     *
     * 总是从外部State变量读取非final类型实例变量能够阻止 这种常量折叠
     *
     */
    private double x = Math.PI;

    private final double wrongX = Math.PI;

    @Benchmark
    public double baseline(){
        return Math.PI;
    }

    @Benchmark
    public double measureWrong_1(){
        return Math.log(Math.PI);
    }

    @Benchmark
    public double measureWrong_2(){
        return Math.log(wrongX);
    }

    @Benchmark
    public double measureRight(){
        return Math.log(x);
    }

    @Test
    public void test() throws RunnerException {
        Options build = new OptionsBuilder()
                .include(BenchMarkConstantFold.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(build).run();
    }
}
