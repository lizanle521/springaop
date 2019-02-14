package com.lzl.dubbodemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author lizanle
 * @Date 2019/2/14 15:10
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        context.start();
        System.in.read();
    }
}
