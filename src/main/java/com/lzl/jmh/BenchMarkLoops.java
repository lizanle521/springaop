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
public class BenchMarkLoops {
    /**
     * 用户一般想在 benchMark方法里边使用循环，
     * 这个例子是想告诉大家为啥这是一个坏主意
     *
     * 循环结束的时候 希望 最小化调用测试方法的负载
     * 通过在循环内部进行 而不是 通过方法的调用。 不要买账。
     * 当我们允许优化器去 合并循环迭代次数的时候 会有一些神奇的事情发生
     */

    /**
     * 假如我们想计算两个整数的和
     */
    int x = 1;
    int y = 2;

    // 如下才是正确的方法
    @Benchmark
    public int measureRight(){
        return x + y;
    }

    private int reps(int reps){
        int s = 0;
        for (int i = 0; i < reps; i++) {
            s += (x+y);
        }
        return s;
    }

    /**
     * 我们将要去测量 reps 在不同的  循环次数下的 性能。
     * 这里我们用到 一个OperationsPerInvocation
     */

    @Benchmark
    @OperationsPerInvocation(1)
    public int measureWrong_1(){
        return reps(1);
    }

    @Benchmark
    @OperationsPerInvocation(10)
    public int measureWrong_10(){
        return reps(10);
    }

    @Benchmark
    @OperationsPerInvocation(100)
    public int measureWrong_100(){
        return reps(100);
    }

    @Benchmark
    @OperationsPerInvocation(1000)
    public int measureWrong_1000(){
        return reps(1000);
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public int measureWrong_10000(){
        return reps(10000);
    }

    @Benchmark
    @OperationsPerInvocation(100000)
    public int measureWrong_100000(){
        return reps(100000);
    }

    /**
     * 最后结果 你发现 每次加法是 50分支1纳秒。远超过硬件实际能做到的时间
     * 因为循环是高度流水化的 。所以不要依赖循环，而依赖jmh的迭代次数
     * @throws RunnerException
     */
    @Test
    public void test() throws RunnerException {
        Options build = new OptionsBuilder()
                .include(BenchMarkLoops.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(build).run();
    }
}
