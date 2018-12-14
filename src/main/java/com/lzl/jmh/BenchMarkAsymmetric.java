package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BenchMarkAsymmetric {
    /**
     * 到目前为止，所有的测试用例都是均衡的：相同的代码在所有的线程里运行。
     * 有时候，你需要不均衡的测试用例。jmh为此提供了 @Group 注解
     *  能够将几个方法绑定到  一起，所有的线程分布在这几个方法中间
     *
     *  每个执行组 包含一个或者多个线程，在特定的执行组中 一个线程只执行 其中的一个方法
     *  多个执行组共同参与这次运行。总的线程数 约等于 执行组的大小
     *  以充分执行整个组
     *
     *  注意 Scope.Thread 和 Scope.BenchMark 。线程将不会覆盖所有的用例 你可以选择 用所有线程共享一个 state
     *  或者 所有线程都不共享。为了打破这种惯例，出现了中间情况 Scope.Group .他表示状态将会在组内共享
     *
     *  总结一下，下边的例子的意思
     *  1）定义一个组 “g",组类有3个线程 执行 inc(),一个线程执行 get().每个组4个线程
     *  2）如果我们用4个线程跑下边的例子，那我们只有一个单一的组。一般来讲，4*N个线程将会创建 N个组
     *  3） 每个执行组共享一个state实例。这就是说，执行组共享组内的 counter 实例，而不是组间的。
     */

    private AtomicInteger counter;

    @Setup
    public void up(){
        counter = new AtomicInteger();
    }

    @Benchmark
    @Group("g")
    @GroupThreads(3)
    public int inc(){
        return counter.incrementAndGet();
    }

    @Benchmark
    @Group("g")
    @GroupThreads(1)
    public int get(){
        return counter.get();
    }

    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder()
                .include(BenchMarkAsymmetric.class.getSimpleName())
                .forks(1)
                .build();
        // 默认预热次数 20  实际测试次数 20
        new Runner(op).run();
    }
}
