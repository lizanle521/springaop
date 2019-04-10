package com.lzl.design_pattern.Q_Observer;

import java.util.Random;

/**
 * @author lizanle
 * @data 2019/4/10 8:33 PM
 */
public class RandNumberGenerator extends NumberGenerator {
    private Random random = new Random();
    private int number;

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void execute() {
        for (int i = 0; i < 20; i++) {
            number = random.nextInt(50);
            notifyObserver();
        }
    }
}
