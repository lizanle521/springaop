package com.lzl.netty.chapter2.nio;

/**
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
