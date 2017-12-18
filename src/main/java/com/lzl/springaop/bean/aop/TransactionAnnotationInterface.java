package com.lzl.springaop.bean.aop;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lizanle on 2017/12/13.
 */
public interface TransactionAnnotationInterface {
   @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
   public  void A();
   @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
   public  void B();
}
