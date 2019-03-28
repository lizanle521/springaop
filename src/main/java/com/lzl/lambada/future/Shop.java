package com.lzl.lambada.future;

import io.netty.util.concurrent.CompleteFuture;
import org.codehaus.plexus.interpolation.os.OperatingSystemUtils;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 模拟场景 ，从各大网店获取一个商品报价
 * @author lizanle
 * @Date 2019/3/14 17:37
 */
public class Shop {
    Random random = new Random();

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double getPice(String product) {
        delay();
        return random.nextDouble() * 100;
    }

    public Future<Double> getPriceAsync(String product){
        return CompletableFuture.supplyAsync(()->getPice(product));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Shop ebay = new Shop("ebay");
        long start = System.currentTimeMillis();
        Future<Double> future = ebay.getPriceAsync("机器人");
        System.out.println("调用返回时间："+(System.currentTimeMillis() - start));
        Double price = future.get();
        System.out.println("获取价格时间："+(System.currentTimeMillis() - start));
    }
}
