package com.lzl.springaop;

import com.lzl.springaop.bean.Animal;
import com.lzl.springaop.bean.Human;
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

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:application.xml");
        Human proxy = (Human)applicationContext.getBean("proxy");
        proxy.sleep();
    }
}
