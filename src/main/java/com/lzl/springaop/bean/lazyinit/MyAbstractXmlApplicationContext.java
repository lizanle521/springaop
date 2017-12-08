package com.lzl.springaop.bean.lazyinit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Lizanle on 2017/12/8.
 */
public class MyAbstractXmlApplicationContext extends ClassPathXmlApplicationContext {
    public MyAbstractXmlApplicationContext() {
        super();
    }

    public MyAbstractXmlApplicationContext(String configLocation) throws BeansException {
        super(configLocation);
    }

    public MyAbstractXmlApplicationContext(String[] configLocations, ApplicationContext parent) throws BeansException {
        super(configLocations, parent);
    }

    public MyAbstractXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent) throws BeansException {
        super(configLocations, refresh, parent);
    }

    public MyAbstractXmlApplicationContext(String path, Class clazz) throws BeansException {
        super(path, clazz);
    }

    public MyAbstractXmlApplicationContext(String[] paths, Class clazz, ApplicationContext parent) throws BeansException {
        super(paths, clazz, parent);
    }

    public MyAbstractXmlApplicationContext(String[] configLocations, boolean refresh) throws BeansException {
        super(configLocations, refresh);
    }

    public MyAbstractXmlApplicationContext(ApplicationContext parent) {
        super(parent);
    }

    public MyAbstractXmlApplicationContext(String[] paths, Class clazz) throws BeansException {
        super(paths, clazz);
    }

    public MyAbstractXmlApplicationContext(String... configLocations) throws BeansException {
        super(configLocations);
    }

    @Override
    protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
        super.initBeanDefinitionReader(reader);
        reader.setEventListener(new MyEventListener());
    }
}
