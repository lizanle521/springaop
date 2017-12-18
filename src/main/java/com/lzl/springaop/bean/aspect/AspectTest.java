package com.lzl.springaop.bean.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by Lizanle on 2017/12/7.
 * 横切关注点
 *
 * 类是对物体特征的抽象，切面就是对横切关注点的抽象
 * 即Aspect就包括了Aop最重要的两个概念，横切关注点，切面
 */
@Aspect
public class AspectTest {
    /**
     * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
     * returning type pattern,name pattern, and parameters pattern是必须的.
     *
     * 切点:对连接点进行拦截的定义,是一系列的连接点的集合
     * 即PointCut 的包括了 连接点 和 切点的
     */
    @Pointcut("execution(public * *.test*(..))")
    public int aop() throws Exception {
        System.out.println("test");
        throw new Exception("a");
        //return 1;
    }

    /**
     * 通知 包括前置通知，后置通知，环绕通知，异常通知，返回通知
     */
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
