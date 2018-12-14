package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchMarkSyncIterations {
    /**
     * 同步迭代是 jmh提供的默认功能
     */

    private double src;

    @Benchmark
    public double sin(){
        double s = src;
        for (int i = 0; i < 1000; i++) {
            s = Math.sin(s);
        }
        return s;
    }

    /**
     * 英语抠脚 。先翻译 再仔细琢磨吧
     * 这个例子证明了 如果你用多线程跑上边的方法，启动和关闭工作线程的方式将会严重影响性能
     *
     * 一个比较自然的方式就是 阻塞所有的线程于一个 屏障上 （barrir),然后让他们一起跑
     * 然而，这并没有什么用。没有任何保障这些线程会同时启动
     * 意味着其他的工作线程能够在更好的状态下运行，扰乱结果
     *
     * 一个更好的办法就是 引入假的迭代
     * 加大线程数量 执行迭代，让他们自动的让整个系统测量充分。速降期间 同样的事情也能做好
     * 听起来有些 复杂。但是jmh已经为你处理好了这些问题 。
     */

    @Test
    public void test() throws RunnerException {
        Options build = new OptionsBuilder()
                .include(BenchMarkSyncIterations.class.getSimpleName())
                .threads(Runtime.getRuntime().availableProcessors() * 16)
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.seconds(1))
                .forks(1)
                .syncIterations(false) // 对于迭代要不要进行同步 true 为 同步  false为不同步
                .build();
        new Runner(build).run();
    }
}
