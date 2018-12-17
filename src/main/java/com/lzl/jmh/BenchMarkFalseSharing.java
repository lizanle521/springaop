package com.lzl.jmh;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5,time=1,timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5,time=1,timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class BenchMarkFalseSharing {
    /**
     * 伪共享这个比较罕见的事情会咬你一口。
     * 如果两个线程访问在高速缓存中相邻的值的话，他们有一定的几率修改这个在同一个缓存行中的值，这样的话就会显著的降低性能
     *
     * jmh能够帮你提升：@state 状态对象通常会自动补齐空白
     * 请看下面的例子 jdk8下的 注解 @Contended性能是最好的。
     */

    /**
     * 因为伪共享的存在，读取线程 和 写入线程都会被拖慢
     *
     */

    @State(Scope.Group)
    public static class BaseLine{
        int readOnly;
        int writeOnly;
    }

    @Benchmark
    @Group("baseline")
    public int reader(BaseLine s){
        return s.readOnly;
    }

    @Benchmark
    @Group("baseline")
    public void writer(BaseLine s){
        s.writeOnly++;
    }

    /**
     * 尝试去通过 补齐空白 去提升
     * 这并不是万能的，因为jvm可能会对这些属性进行重排序
     */
    @State(Scope.Group)
    public static class StatePadded{
        int readOnly;
        int p01, p02, p03, p04, p05, p06, p07, p08;
        int p11, p12, p13, p14, p15, p16, p17, p18;
        int writeOnly;
        int q01, q02, q03, q04, q05, q06, q07, q08;
        int q11, q12, q13, q14, q15, q16, q17, q18;
    }

    @Benchmark
    @Group("padded")
    public int paddedreader(StatePadded s){
        return s.readOnly;
    }

    @Benchmark
    @Group("padded")
    public void paddedwriter(StatePadded s){
        s.writeOnly++;
    }


    /**
     * 通过继承的方式，因为父类的属性会排在子类的属性前面
     */

    public static class StateHierarchy_1 {
        int readOnly;
    }

    public static class StateHierarchy_2 extends StateHierarchy_1 {
        int p01, p02, p03, p04, p05, p06, p07, p08;
        int p11, p12, p13, p14, p15, p16, p17, p18;
    }

    public static class StateHierarchy_3 extends StateHierarchy_2 {
        int writeOnly;
    }

    public static class StateHierarchy_4 extends StateHierarchy_3 {
        int q01, q02, q03, q04, q05, q06, q07, q08;
        int q11, q12, q13, q14, q15, q16, q17, q18;
    }

    @State(Scope.Group)
    public static class StateHierarchy extends StateHierarchy_4 {
    }


    @Benchmark
    @Group("hirarchy")
    public int hirarchydreader(StateHierarchy s){
        return s.readOnly;
    }

    @Benchmark
    @Group("hirarchy")
    public void hirarchydwriter(StateHierarchy s){
        s.writeOnly++;
    }

    /**
     * 下边的技巧就是数组的内存空间是连续分配的
     * 不是把属性放在类里边，我们将 属性打乱 放在 稀疏数组里边
     */

    @State(Scope.Group)
    public static class StateArray{
        int[] arr = new int[128];
    }

    @Benchmark
    @Group("array")
    public int arrayreader(StateArray s){
        return s.arr[0];
    }

    @Benchmark
    @Group("array")
    public void arraywriter(StateArray s){
        s.arr[64]++;
    }

    /**
     * 在jdk8中 可以用 sum.misc.Contented 注解
     * 记得用 虚拟机参数 -XX:-RestrictContended 来开启自动补齐
     */
    @State(Scope.Group)
    public static class StateContented {
        int readOnly;

        @sun.misc.Contended
        int writeOnly;
    }

    @Benchmark
    @Group("content")
    public int contentreader(StateContented s){
        return s.readOnly;
    }

    @Benchmark
    @Group("content")
    public void contentwriter(StateContented s){
        s.writeOnly++;
    }

    @Test
    public void test() throws RunnerException {
        Options build = new OptionsBuilder()
                .include(BenchMarkFalseSharing.class.getSimpleName())
                .threads(Runtime.getRuntime().availableProcessors())
                .build();
        new Runner(build).run();
    }

}
