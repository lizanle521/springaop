package com.lzl.springaop.bean.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
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
    @Pointcut("execution(public * *.test*(..))")
    public int aop() throws Exception {
        System.out.println("test");
        throw new Exception("a");
        //return 1;
    }

    @Before("aop()")
    public void beforetest(){
        System.out.println("beforetest");
    }

    @After("aop()")
    public void aftertest(){
        System.out.println("aftertest");
    }

    @Around("aop()")
    public void aroundtest(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("aroundtest before");
        proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        System.out.println("aroundtest afeter");

    }

    @AfterReturning("aop()")
    public void afterreturn(){
        System.out.println("afterreturing");
    }

    @AfterThrowing("aop()")
    public void afterthrowingtest(){
        System.out.println("afterthrowingtest");
    }

}
