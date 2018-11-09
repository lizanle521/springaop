package com.lzl.springaop;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyRunnable implements Runnable {
    private static Integer i = new Integer(0);

    @Override
    public void run() {
        while (true){
            synchronized (i){
                if(i<100){
                    i++;
                    System.out.println(Thread.currentThread().getId()+" i : " + i);
                }else{
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyRunnable());
        Thread t2 = new Thread(new MyRunnable());
        t1.start();
        t2.start();
        // 运行结果 ：无序打印 i=1 到 i=100,总体情况有序递增，个别无序
    }
}
