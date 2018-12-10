package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchMarkState {
    /**
     * 大多数时候，当jmh在运行的时候你需要维护一些状态。
     * 由于 jmh做性能测试 是高度并行的，我们用一些指定的对象来存储状态 来做优化
     *
     * 下边是两个状态对象，他们的类名并不是关键点，关键是 他们被 @State 注解 标识了
     * 这些对象将在被需要的时候实例化，并且在整个性能测试期间都被使用
     *
     * 最重要的属性是，虽然所有的工作线程都可以访问状态对象，但是 状态对象 总是被性能测试的线程之一所实例化，
     * 这意味着 你能够在所有的工作线程中对 状态对象进行操作。
     */
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        volatile double x =  Math.PI;
    }

    @State(Scope.Thread)
    public static class ThreadState {
        volatile double x = Math.PI;
    }

    /**
     *  BenchMark 方法能够引用到这些状态，并且jmh在调用方法的时候能够适当的时机对这些状态对象进行注入
     *  你没有任何状态对象 或者有一个状态对象 或者 有 很多状态对象引用
     *  这将使得jmh 编写多线程的性能测试 变的轻而易举。
     *
     */
    @Benchmark
    public void measureUnshared(ThreadState state) throws Exception {
        //所有的benchmark的工作线程将会调用这个方法
        //然而，如果Scope为 Thread的话，每个线程将会有这个State的拷贝
        // 这里jmh将测试 不共享的 state
        state.x ++;
    }

    @Benchmark
    public void measureShared(BenchmarkState state) throws Exception {
        // 这里是测试所有的jmh工作线程共享 一个 state的表现
        state.x ++;
    }

    @Test
    public void test() throws Exception{
        Options op = new OptionsBuilder()
                .include(BenchMarkState.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();
        new Runner(op).run();
    }
}
