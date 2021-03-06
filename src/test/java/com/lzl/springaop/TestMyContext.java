package com.lzl.springaop;

import com.lzl.springaop.bean.aspect.TestBean;
import com.lzl.springaop.bean.aspect.TestBeanIF;
import com.lzl.springaop.bean.lazyinit.MyAbstractXmlApplicationContext;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Lizanle on 2017/12/8.
 */
public class TestMyContext {

    @Test
    public void testMyContext() throws Exception {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        MyAbstractXmlApplicationContext context = new MyAbstractXmlApplicationContext("classpath*:application.xml");
        TestBeanIF testBean = (TestBeanIF)context.getBean("testBean");
        //testBean.testA();
        /**
         * aroundtest before
         beforetest
         test
         aroundtest afeter
         aftertest
         afterreturing
         */

        /**
         * aroundtest before
         beforetest
         aftertest
         afterthrowingtest
         */
        //System.in.read();
    }

}
