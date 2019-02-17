package com.lzl.netty.chapter2.nio;

/**
 * @author lizanle
 * @data 2019/2/17 8:12 PM
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8081;
        if(args != null && args.length > 0){
            try {
                Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        new Thread(new TimeClientHandler("127.0.0.1",port),"timeclienthandler-001").start();

    }
}
