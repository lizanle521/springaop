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
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchMarkRunToRun {
    /**
     * fork 可以用来估计 批次方差
     * jvm是一个复杂的系统，具有与生俱来的不确定性
     * 这就要求我们总是去审计 一个 批次方差 作为我们的实验结果
     * 幸运的是，不同jvm的运行结果能够被统计
     */

    /**
     * 为了能够引入真实环境的 批次 方差，
     * 我们用随机休眠时间来模拟不同批次的负载，注意不同的负载会有相同的行为，但是我们仍然手动去做这个。
     */
    @State(Scope.Thread)
    public static class SleepState {
        public long sleepTime;

        @Setup
        public void setup(){
            // 这里的取值 为 0-1000. 最后的结果预期应该为 500ms
            sleepTime = (long) (Math.random()*1000);
        }
    }

    @Benchmark
    @Fork(1)
    public void baseLine(SleepState s) throws Exception {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    @Benchmark
    @Fork(5)
    public void fork1(SleepState s) throws Exception {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    @Benchmark
    @Fork(20)
    public void for2(SleepState s) throws Exception {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    @Test
    public void test() throws RunnerException {
        Options build = new OptionsBuilder()
                .include(BenchMarkRunToRun.class.getSimpleName())
                .warmupIterations(0)
                .measurementIterations(3)
                .build();
        new Runner(build).run();
    }
}
