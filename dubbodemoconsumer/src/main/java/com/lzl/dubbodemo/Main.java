package com.lzl.dubbodemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lizanle
 * @Date 2019/2/14 15:26
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        context.start();
        DemoService demoService = (DemoService)context.getBean("demoService");
        String s = demoService.sayHello("lizanle");
        System.out.println(s);
    }
}
