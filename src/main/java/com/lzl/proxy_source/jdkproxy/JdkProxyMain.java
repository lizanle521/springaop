package com.lzl.proxy_source.jdkproxy;

import java.lang.reflect.Proxy;

public class JdkProxyMain {
    public static void main(String[] args) {
        jdkProxy();
    }

    private static void jdkProxy(){
        HelloService helloService = new HelloServiceImpl();
        InvocationHandlerImpl handler = new InvocationHandlerImpl(helloService);
        HelloService service = (HelloService)Proxy.newProxyInstance(HelloService.class.getClassLoader(), new Class[]{HelloService.class}, handler);
        String s = service.sayHello("lzl");
        System.out.println(s);
    }
}
