package com.lzl.dubbodemo;

/**
 * @author lizanle
 * @Date 2019/2/14 11:35
 */

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return name+" hello";
    }
}
