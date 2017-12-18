package com.lzl.springaop.bean.aop;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lizanle on 2017/12/13.
 */
@Component
public class TransactionAnnotation implements TransactionAnnotationInterface {

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void A(){
        //((TransactionAnnotationInterface)AopContext.currentProxy()).B();
        this.B();
    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NEVER)
    public void B(){

    }
}
