package com.lzl.thirdjarmanage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Set;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Lizanle on 2018/2/8.
 */
public class ThirdJarScannerConfigure implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {
    private String beanName;

    private ApplicationContext applicationContext;
    /**
     * 指定扫描路径
     */
    private String basePackage;
    /**
     * 标记接口
     */
    private Class<?> markInterface;
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Class<?> getMarkInterface() {
        return markInterface;
    }

    public void setMarkInterface(Class<?> markInterface) {
        this.markInterface = markInterface;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");
        notNull(this.markInterface, "Property 'markInterface' is required");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ThirdJarClassPathBeanDefinitionScanner scanner = new ThirdJarClassPathBeanDefinitionScanner(registry);

        scanner.setMarkInterface(markInterface);
        Set<BeanDefinitionHolder> scan = scanner.doScan(basePackage);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
