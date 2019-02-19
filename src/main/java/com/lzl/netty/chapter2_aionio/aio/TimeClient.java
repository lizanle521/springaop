package com.lzl.netty.chapter2_aionio.aio;

/**
 * Nio 的异步套接字是真正的异步非阻塞编程，对于unix网络编程中的事件驱动aio,它不需要通过多路复用器对注册的通道进行轮询即可
 * 实现异步读写，简化了nio的编程模型
 * @author lizanle
 * @data 2019/2/19 2:59 PM
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8081;
        if(args != null && args.length > 0){
            try {
                Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        AsyncTimeClientHandler clientHandler = new AsyncTimeClientHandler("127.0.0.1",port);
        new Thread(clientHandler,"AIO-asyncTimeServerHandler").start();
    }
}
