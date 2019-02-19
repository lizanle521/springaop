package com.lzl.netty.chapter2_aionio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author lizanle
 * @data 2019/2/19 3:00 PM
 */
public class AsyncTimeServerHandler implements Runnable {

    private int port;

    // 防止异步线程操作没有完成就退出而设计的
    CountDownLatch countDownLatch;

    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("time server started at port:"+port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        doAccept();
        try {
            countDownLatch.await(); // 如果是调用Object的wait()的话，将会出现IllegalMonitorStateException.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAccept(){
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
