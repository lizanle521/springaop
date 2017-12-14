package com.lzl.springaop.bean.aspect;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lizanle on 2017/12/7.
 */
@Component("testBean")
public class TestBean implements TestBeanIF, SelfAware {
    private TestBeanIF testBean;
    @Override
    public void setSelf(Object proxyBean) {
        testBean = (TestBeanIF)proxyBean;
    }

    private String teststr = "testStr";

    public TestBean() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getTeststr() {
        return teststr;
    }

    public void setTeststr(String teststr) {
        this.teststr = teststr;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void testA() throws Exception {
        //throw new Exception("");
        System.out.println("test");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testB() throws Exception{
        //testBean.testA();
        ((TestBeanIF) AopContext.currentProxy()).testA();
    }
}
