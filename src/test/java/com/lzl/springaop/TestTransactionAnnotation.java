package com.lzl.springaop;

import com.lzl.springaop.bean.aop.TransactionAnnotation;
import com.lzl.springaop.bean.aop.TransactionAnnotationInterface;
import org.junit.Test;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Lizanle on 2017/12/13.
 */
public class TestTransactionAnnotation  {
    @Test
    public void testTransactionAnnotation(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:application-transaction.xml");
        TransactionAnnotation transactionAnnotation = (TransactionAnnotation) applicationContext.getBean("transactionAnnotation");
        System.out.println(AopContext.currentProxy());
        transactionAnnotation.A();
    }
}
