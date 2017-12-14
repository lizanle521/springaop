package com.lzl.springaop.bean.aspect;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by Lizanle on 2017/12/14.
 */
public class SelfInjectionBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof SelfAware){
            ((SelfAware) bean).setSelf(bean);
        }
        return bean;
    }
}
