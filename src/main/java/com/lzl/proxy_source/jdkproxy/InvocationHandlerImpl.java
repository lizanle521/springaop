package com.lzl.proxy_source.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerImpl implements InvocationHandler {
    Object target;

    public InvocationHandlerImpl(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke method:"+method.getName());
        Object result = method.invoke(target,args);
        System.out.println("result:" + result);
        return result;
    }
}
