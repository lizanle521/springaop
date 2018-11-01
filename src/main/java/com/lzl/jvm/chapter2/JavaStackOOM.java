package com.lzl.jvm.chapter2;

import org.junit.Test;

/**
 * 容易把机器搞死，谨慎运行
 *  所以在操作系统内存恒定的情况下，每个线程分配的栈容量越大，可以创建的线程数就越少，对应的创建一个新的线程就越容易发生内存溢出。
 *  当系统内存溢出又无法减少线程数或者加大总内存的情况下可以尝试减少栈的-Xss值创建新的线程
 * -Xss2m 
 */
public class JavaStackOOM {
    private void dontStop(){
        while (true){
           
        }
    }

    public void stackLeakByThread(){
        while (true){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    @Test
    public void testStackLeakByThread(){
        stackLeakByThread();
    }

    public static void main(String[] args) {
        JavaStackOOM javaStackOOM = new JavaStackOOM();
        javaStackOOM.stackLeakByThread();
    }
}
