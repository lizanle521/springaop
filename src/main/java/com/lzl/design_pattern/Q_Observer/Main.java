package com.lzl.design_pattern.Q_Observer;

/**
 * @author lizanle
 * @data 2019/4/10 8:40 PM
 */
public class Main {
    public static void main(String[] args) {
        RandNumberGenerator generator = new RandNumberGenerator();
        DigitObserver digitObserver = new DigitObserver();
        GraphObserver graphObserver = new GraphObserver();
        generator.addObserver(digitObserver);
        generator.addObserver(graphObserver);
        generator.execute();
    }
}
