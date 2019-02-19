package com.lzl.netty.chapter2.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author lizanle
 * @data 2019/2/19 4:15 PM
 */
public class AsyncTimeClientHandler implements CompletionHandler<Void,AsyncTimeClientHandler>,Runnable {

    private AsynchronousSocketChannel asynchronousSocketChannel;

    private String host;

    private int port;

    private CountDownLatch countDownLatch;

    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            asynchronousSocketChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        asynchronousSocketChannel.connect(new InetSocketAddress(host,port),this,this);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();

        asynchronousSocketChannel.write(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if(attachment.hasRemaining()){
                    asynchronousSocketChannel.write(attachment,attachment,this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                asynchronousSocketChannel.read(readBuffer,
                        readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {
                                attachment.flip();
                                byte[] bytes = new byte[attachment.remaining()];
                                attachment.get(bytes);
                                String body;
                                try {
                                    body = new String(bytes,"utf-8");
                                    System.out.println("now is" + body);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }finally {
                                    countDownLatch.countDown();
                                }
                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer attachment) {
                                countDownLatch.countDown();
                                try {
                                    asynchronousSocketChannel.close();
                                } catch (IOException e) {

                                }
                            }
                        });
            }
        });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        countDownLatch.countDown();
        try {
            asynchronousSocketChannel.close();
        } catch (IOException e) {

        }
    }
}
