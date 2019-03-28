package com.lzl.design_pattern.J_Strategy.classes;

import com.lzl.design_pattern.J_Strategy.Interfaces.Strategy;

import java.util.Random;

/**
 * @author lizanle
 * @data 2019/3/28 8:41 PM
 */
public class WinningStrategy implements Strategy {
    private Random random;
    private boolean won = false;
    private Hand preHand;

    public WinningStrategy(int seed) {
        random = new Random(seed);
    }

    @Override
    public Hand nextHand() {
        if(!won){
            preHand = Hand.getHand(random.nextInt(3));
        }
        return preHand;
    }

    @Override
    public void stuty(boolean win) {
        won = win;
    }
}
