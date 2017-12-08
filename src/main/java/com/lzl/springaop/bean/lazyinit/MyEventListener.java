package com.lzl.springaop.bean.lazyinit;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.*;
import org.springframework.beans.factory.xml.DocumentDefaultsDefinition;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.util.Collections;

/**
 * Created by Lizanle on 2017/12/8.
 */
public class MyEventListener implements ReaderEventListener {
    @Override
    public void defaultsRegistered(DefaultsDefinition defaultsDefinition) {
        System.out.println("defaultsRegistered");
        if(defaultsDefinition instanceof DocumentDefaultsDefinition){
            ((DocumentDefaultsDefinition) defaultsDefinition).setLazyInit("true");
        }
    }

    @Override
    public void componentRegistered(ComponentDefinition componentDefinition) {
        System.out.println("componentRegistered");
        BeanDefinition[] beanDefinitions = componentDefinition.getBeanDefinitions();
        if(beanDefinitions.length != 0){
            for (BeanDefinition definition : beanDefinitions) {
                System.out.println(definition.getBeanClassName());
            }
        }
    }

    @Override
    public void aliasRegistered(AliasDefinition aliasDefinition) {
        System.out.println("aliasRegistered");
    }

    @Override
    public void importProcessed(ImportDefinition importDefinition) {
        System.out.println("importProcessed");
    }
}
