package com.lzl.springaop.bean;

import org.springframework.aop.AfterAdvice;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by Lizanle on 2017/11/29.
 */
public class SleepHelper implements MethodBeforeAdvice, AfterReturningAdvice{
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before sleep");
    }

    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("after sleep");
    }
}
