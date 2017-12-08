package com.lzl.springaop.bean.aspect;

import org.springframework.stereotype.Component;

/**
 * Created by Lizanle on 2017/12/7.
 */
@Component("testBean")
public class TestBean {
    private String teststr = "testStr";

    public TestBean() {
        try {
            Thread.sleep(5000);
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

    public void testA() throws Exception {
        throw new Exception("");
        //System.out.println("test");
    }
}
