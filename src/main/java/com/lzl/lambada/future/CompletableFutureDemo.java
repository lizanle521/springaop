package com.lzl.lambada.future;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static java.lang.Thread.sleep;

/**
 * @author lizanle
 * @Date 2019/3/15 17:50
 */
public class CompletableFutureDemo {
    @Test
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread().getName());
        // supplyAsync 是异步执行，异步执行就是不会和当前主线程一起执行
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return 1;});
        assert future.get() == 1;


    }

    @Test
    public void testCompletableFuture_thenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        });

        // thenCombine 是组合两个future的结果，所以(a,b)中的两个a,b是两个future返回的结果类型
        CompletableFuture<Integer> future1 = future.thenCombine(CompletableFuture.supplyAsync(() -> {
            return 2;
        }), (a, b) -> {
            return a + b;
        });
        System.out.println(future1.get());
    }

    @Test
    public void testCompletableFuture_thenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        });

        // thenCompose 是组合两个future的本身,组合的过程中会先计算出future的结果 r，然后传入新的future
        CompletableFuture<Integer> future1 = future.thenCompose(r -> {
            return CompletableFuture.supplyAsync(()->{return r+2;});
        });
        System.out.println(future1.get());
    }

    @Test
    public void testCompletableFuture_either() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        // future 和 CompletableFuture.supplyAsync(()->{return 2;})，哪个先完成就调用 最后的 加法
        // 譬如这个future要2s,而CompletableFuture.supplyAsync(()->{return 2;})马上返回，所以结果是2+3=5
        CompletableFuture<Integer> future1 = future.applyToEitherAsync(
                CompletableFuture.supplyAsync(()->{return 2;}),
                (a)->{
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return a + 3;});
        System.out.println(future1.get());

    }

    @Test
    public void testCompletableFuture_whenCompleteAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        });

        CompletableFuture<Integer> future1 = future.whenCompleteAsync((r,t) -> {
            throw new RuntimeException(t);
        });
        System.out.println(future1.get());
    }

    @Test
    public void testCompletableFuture_handle() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 1;
        });

        CompletableFuture<Integer> future1 = future.handle((r,t) -> {
            return 1;
        });
        System.out.println(future1.get());
    }


    @Test
    public void testCompletableFuture_order() throws Exception{
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                sleep(1); // TimeUnit.SECONDS.sleep(1)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result f1";
        });
        CompletableFuture<String> f2 = f1.thenApply(r -> {
            System.out.println(r);
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result f2";
        });
        CompletableFuture<String> f3 = f2.thenApply(r -> {
            System.out.println(r);
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result f3";
        });

        CompletableFuture<String> f4 = f3.thenApply(r -> {
            System.out.println(r);
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result f4";
        });
        CompletableFuture<String> f5 = f4.thenApply(r -> {
            System.out.println(r);
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result f5";
        });
        CompletableFuture<String> f6 = f5.thenApply(r -> {
            System.out.println(r);
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result f6";
        });
        System.out.println(f6.get());
        /**
         * result f1
         result f2
         result f3
         result f4
         result f5
         result f6
         全都是顺序输出结果
         */
    }
}
