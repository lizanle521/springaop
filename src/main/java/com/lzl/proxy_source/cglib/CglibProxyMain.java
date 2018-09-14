package com.lzl.proxy_source.cglib;

import com.lzl.proxy_source.jdkproxy.HelloService;
import com.lzl.proxy_source.jdkproxy.HelloServiceImpl;
import org.springframework.cglib.proxy.Enhancer;

public class CglibProxyMain {
    public static void main(String[] args) {
        cglibProxy();
    }

    private static void cglibProxy(){
        HelloService helloService = new HelloServiceImpl();
        MethodInterceptorImpl interceptor = new MethodInterceptorImpl(helloService);
        HelloService o = (HelloService)Enhancer.create(HelloService.class, interceptor);
        String s = o.sayHello("lzl");
        System.out.println(s);
    }
}
