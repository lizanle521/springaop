package com.lzl.springaop.bean.aspect;

import org.aspectj.lang.annotation.*;

/**
 * Created by Lizanle on 2017/12/7.
 */
@Aspect
public class AspectTest {
    /**
     * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
     * returning type pattern,name pattern, and parameters pattern是必须的.
     */
    @Pointcut("execution(public * *.test(..))")
    public int test() throws Exception {
        //throw new Exception("a");
        return 1;
    }

    @Before("test()")
    public void beforetest(){
        System.out.println("beforetest");
    }

    @After("test()")
    public void aftertest(){
        System.out.println("aftertest");
    }

    @Around("test()")
    public void aroundtest(){
        System.out.println("aroundtest");
    }

    @AfterReturning("test()")
    public void afterreturn(){
        System.out.println("afterreturing");
    }

    @AfterThrowing("test()")
    public void afterthrowingtest(){
        System.out.println("afterthrowingtest");
    }

}
