package com.lzl.netty.chapter2_aionio.nio;

/**
 * nio编程的优点：
 * 1. 客户端发起的连接操作是异步的，可以通过多路复用器注册op_connect等待后续结果，不需要像之前的客户端那样被同步阻塞
 * 2. socketchannel的读写操作都是异步的，如果没有可读写的数据它不会进行同步等待，直接返回，这样io通信线程可以处理其他的
 * 连接，不需要同步等待这个链路可用
 * 3. 线程模型的优化，由于jdk的多路复用器在linux等主流操作系统上通过epoll实现，它没有句柄数的限制，这意味着一个多路复用线程
 * 可以处理成千上万的客户端连接，而且性能不会随着客户端的增加而线性下降。非常适合做高性能，高负载的网络服务器
 * @author lizanle
 * @data 2019/2/17 2:25 PM
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8081;
        if(args != null && args.length > 0){
            try {
                Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
            }
        }

        MultiplexerTimeServer server = new MultiplexerTimeServer(port);
        new Thread(server,"NIO-MultiplexeTimeServer-001").start();
    }
}
