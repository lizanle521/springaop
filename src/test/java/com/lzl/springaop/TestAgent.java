package com.lzl.springaop;

import org.junit.Test;

/**
 * 测试agent
 * @author lizanle
 * @Date 2019/1/7 11:44
 */
public class TestAgent {
    public Object test() throws InterruptedException {
        System.out.println("test exec");
        Thread.sleep(100);
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        TestAgent testAgent = new TestAgent();
        testAgent.test();
    }
}
