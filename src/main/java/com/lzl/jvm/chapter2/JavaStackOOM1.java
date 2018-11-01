package com.lzl.jvm.chapter2;

import java.util.concurrent.CountDownLatch;

/**
 * 验证堆栈溢出更好的方式。 JavaStackOOM那个类会占用cpu,导致系统很卡
 *  -Xmx20m -Xms20m -Xss2m
 */
public class JavaStackOOM1 {

    static class HoldThread extends Thread {
        CountDownLatch cdl = new CountDownLatch(1);
        public HoldThread() {
            this.setDaemon(true);
        }

        public void run() {
            try {
                cdl.await();
            } catch (InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {
            new Thread(new HoldThread()).start();
        }
    }
}
