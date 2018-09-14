package com.lzl.proxy_source.cglib;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MethodInterceptorImpl implements MethodInterceptor {
    private Object target ;

    public MethodInterceptorImpl(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before cglib proxy ");
        Object invoke = methodProxy.invoke(target, objects);
        System.out.println("after cglib proxy ");

        return invoke;
    }
}
