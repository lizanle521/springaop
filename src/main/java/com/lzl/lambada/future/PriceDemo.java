package com.lzl.lambada.future;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.util.StopWatch;


import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 模拟场景：从多个商店查询某件商品的价格
 * @author lizanle
 * @Date 2019/3/14 18:00
 */
public class PriceDemo {
    private List<Shop> shopList = Arrays.asList(new Shop[]{new Shop("1"),new Shop("2"),
            new Shop("3"),new Shop("4"),
            new Shop("5"),new Shop("6"),
            new Shop("7"),new Shop("8")}
            );

    @SuppressWarnings("unchecked")
    public List<String> getPrice(String product){
        return shopList.stream().map(shop->String.format("%s price is %.2f",shop.getName(),shop.getPice(product))).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<String> getPriceParallel(String product){
        return shopList.parallelStream().map(shop->String.format("%s price is %.2f",shop.getName(),shop.getPice(product))).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<String> getPriceFuture(String product){
        List<CompletableFuture<String>> collect = shopList.stream().map(
                shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPice(product))))
                .collect(Collectors.toList());
        return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<String> getPriceFutureWithExecutor(String product){
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<CompletableFuture<String>> collect = shopList.stream().map(
                shop -> CompletableFuture.supplyAsync(
                                () -> String.format("%s price is %.2f", shop.getName(), shop.getPice(product)),threadPool))
                .collect(Collectors.toList());
        return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<String> getPriceFutureWithExtExecutor(String product){
        ExecutorService threadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(t);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
            }
        };
        List<CompletableFuture<String>> collect = shopList.stream().map(
                shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPice(product)),threadPool))
                .collect(Collectors.toList());
        return collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        PriceDemo priceDemo = new PriceDemo();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("getPrice");
        System.out.println(priceDemo.getPrice("苹果"));
        stopWatch.stop();
        stopWatch.start("getPriceParallel");
        System.out.println(priceDemo.getPriceParallel("苹果"));
        stopWatch.stop();

        stopWatch.start("getPriceFuture");
        System.out.println(priceDemo.getPriceFuture("苹果"));
        stopWatch.stop();

        stopWatch.start("getPriceFutureWithExecutor");
        System.out.println(priceDemo.getPriceFutureWithExecutor("苹果"));
        stopWatch.stop();

        stopWatch.start("getPriceFutureWithExtExecutor");
        System.out.println(priceDemo.getPriceFutureWithExtExecutor("苹果"));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());


        /**
         *-----------------------------------------
         ms     %     Task name
         -----------------------------------------
         08071  057%  getPrice
         02010  014%  getPriceParallel
         03002  021%  getPriceFuture
         01014  007%  getPriceFutureWithExecutor
         01006  007%  getPriceFutureWithExtExecutor


         并行流需要2秒，这是因为并行线程池是核心数是4核，只有4个线程。每个线程跑两个，所以要2秒

         异步需要3秒，因为completableFuture默认启动和并行流一样

         CompletableFuture可以配置线程池,配置线程池以后，耗时只需1秒,用CacheThreadPool直接new了8个线程去跑任务

         */
    }
}
