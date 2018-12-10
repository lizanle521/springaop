package com.lzl.jmh;

import com.lzl.algo.leetCode.array.easy.QucikSort;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchMarkTest {

    @Test
    public void benchMarkTest() throws Exception {
        Options op = new OptionsBuilder()
                .include(BenchMarkTest.class.getSimpleName())
                .warmupIterations(10)
                .measurementIterations(10)
                .forks(3)
                .build();
        new Runner(op).run();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput,Mode.AverageTime})
    public void xxx() {
        int[] nums = new int[]{1,2,4,3,7,5,6};
        QucikSort.sort(nums);
    }
}
