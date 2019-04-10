package com.lzl.design_pattern.Q_Observer;

/**
 * @author lizanle
 * @data 2019/4/10 8:35 PM
 */
public class DigitObserver implements Observer {
    public void update(NumberGenerator numberGenerator){
        System.out.println("DigitObserver:"+numberGenerator.getNumber());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
