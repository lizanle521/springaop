package com.lzl.springaop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyRunnable implements Runnable {
    private static Integer i = new Integer(0);
    private static List<String> list = new LinkedList<String>();
    @Override
    public void run() {
        while (true){
            synchronized (i){
                if(i<100){
                    i++;//这里切换了对象  i++ =>  i = Integer.valueOf(i.intValue() + 1);
                    try {
                        Thread.sleep(10); // sleep不释放锁，如果同步的话 ，这里加上sleep对结果不会有影响。不同步则会有影响
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //list.add(Thread.currentThread().getName() +i);
                    System.out.println(Thread.currentThread().getName()+" --i : " + i );
                    //logger.info(Thread.currentThread().getId()+" i : " + i);
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
//        Thread.currentThread().join(1000);
//        for (String s : list) {
//            System.out.println(s);
//        }
        // 运行结果 ：无序打印 i=1 到 i=100,总体情况有序递增，个别无序 个别重复
    }
}
