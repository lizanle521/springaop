package com.lzl.springaop;

import com.lzl.springaop.bean.aop.TransactionAnnotation;
import com.lzl.springaop.bean.aop.TransactionAnnotationInterface;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Lizanle on 2017/12/13.
 */
public class TestTransactionAnnotation  {
    private final  static  Logger LOGGER = LoggerFactory.getLogger(TestTransactionAnnotation.class);
    @Test
    public void testTransactionAnnotation(){
        LOGGER.info("aaa");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:application-transaction.xml");
        TransactionAnnotationInterface transactionAnnotation = (TransactionAnnotationInterface) applicationContext.getBean("transactionAnnotation");
        //System.out.println(AopContext.currentProxy());
        transactionAnnotation.A();
    }
}
