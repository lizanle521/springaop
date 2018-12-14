package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  维护级别有几种，用来控制benchMark运行。
 *
 */
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BenchMarkLevelInvocation {


    @State(Scope.Benchmark)
    public static class NormalStat {
        public NormalStat() {
            System.out.println("NormalStat init");
        }

        ExecutorService executorService;

        @Setup(Level.Trial)
        public void set(){
            System.out.println("start set");
            executorService = Executors.newCachedThreadPool();
        }

        @TearDown(Level.Trial)
        public void down(){
            System.out.println("shut down");
            executorService.shutdown();
        }
    }

    public static class LaggingState extends NormalStat {
        public static final int SLEEP_TIME = Integer.getInteger("sleepTime",10);

        @Setup(Level.Invocation)
        public void lag() throws Exception {
            System.out.println("lag before");
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        }
    }

    @State(Scope.Thread)
    public static class Scrach {
        public Scrach() {
            System.out.println("scrach init");
        }

        private double p;
        public double doWork(){
            double log = Math.log(p);
            return log;
        }
    }

    public static class Task implements Callable<Double> {
        private Scrach s;

        public Task(Scrach s) {
            this.s = s;
        }

        @Override
        public Double call() throws Exception {
            return s.doWork();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public double measureHot (NormalStat e,Scrach s) throws Exception {
        return e.executorService.submit(new Task(s)).get();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public double measureCold (LaggingState e,Scrach s) throws Exception {
        return e.executorService.submit(new Task(s)).get();
    }

    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder().include(BenchMarkLevelInvocation.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(op).run();
    }
}
