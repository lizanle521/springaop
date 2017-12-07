package com.lzl.springaop;

import com.lzl.springaop.bean.Animal;
import com.lzl.springaop.bean.Human;
import com.lzl.springaop.bean.aspect.TestBean;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Lizanle on 2017/11/29.
 */
public class TestSpringAop {
    @Test
    public void test(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:application.xml");
        Human proxy = (Human)applicationContext.getBean("proxy");
        proxy.sleep();
    }

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:application.xml");
        Human proxy = (Human)applicationContext.getBean("proxy");
        proxy.sleep();
        Thread thread = new Thread() {
            public void run() {
                System.out.println(1);
            }
        };
        thread.start();
        synchronized (thread) {
            thread.wait(0);
        }
    }

    @Test
    public void testAspect(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:application.xml");
        TestBean testBean = (TestBean)context.getBean("testBean");
        testBean.test();
    }

}
