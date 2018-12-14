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
public class BenchMarkCompilerControll {
    /**
     * 我们能够用hostspot虚拟机特定的功能去告诉编译器我们对于指定的方法想做的事情，为了证明这个效果
     * 我们用3个方法来结束这个例子
     */

    /**
     * 以下是我们的目标：
     * 1.第一个方法 禁止内联
     * 2. 第二个方法内联
     * 3. 第三个方法内联
     *
     * 我们将注解直接放到方法上 表达的意图更加明显
     */

    public void target_blank(){
        // 这个方法有意留空
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void target_dontInline(){

    }

    /**
     * 方法内联类似于 c里边的宏，直接替换，免去了方法栈的出入栈
     * 方法内联 是 jit控制的，方法太大会导致内联失败，所以尽可能的用小方法
     */
    @CompilerControl(CompilerControl.Mode.INLINE)
    public void target_inline(){

    }

    @CompilerControl(CompilerControl.Mode.EXCLUDE)
    public void target_exclude(){

    }

    @Benchmark
    public void baseline(){

    }

    @Benchmark
    public void blank(){
        target_blank();
    }

    @Benchmark
    public void dontinline(){
        target_dontInline();
    }

    @Benchmark
    public void inline(){
        target_inline();
    }

    @Benchmark
    public void exclude(){
        target_exclude();
    }


    @Test
    public void test() throws RunnerException {
        Options op = new OptionsBuilder()
                .include(BenchMarkCompilerControll.class.getSimpleName())
                //.jvmArgsAppend("-XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining")
                .warmupIterations(0)
                .measurementIterations(3)

                .forks(1)
                .build();
        // 默认预热次数 20  实际测试次数 20
        new Runner(op).run();
    }

}
