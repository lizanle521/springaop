package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class BenchMarkBlackHole {
    /**
     * 如果方法里边有多个返回结果，但是实际只返回了一个，那么没有被返回的结果就会被优化掉
     * 怎么防止呢
     */
     double x1 = Math.PI;
     double x2 = Math.PI*2;

     @Benchmark
    public double baseLine() {
         return Math.log(x1);
     }

     @Benchmark
    public double measureWrong(){
         Math.log(x1); // 这里会被优化掉
         return Math.log(x2);
     }

     @Benchmark
    public double measureRight(){
         return Math.log(x1)+Math.log(x2);
     }

     @Benchmark
    public void measureRight_1(Blackhole bh){
         bh.consume(Math.log(x1));
         bh.consume(Math.log(x2));
     }

     @Test
    public void test() throws RunnerException {
         Options build = new OptionsBuilder()
                 .include(BenchMarkBlackHole.class.getSimpleName())
                 .forks(1)
                 .build();
         new Runner(build).run();
     }
}
