package com.lzl.design_pattern.J_Strategy.classes;

import com.lzl.design_pattern.J_Strategy.Interfaces.Strategy;

import java.util.Random;

public class ProbStrategy implements Strategy {
    private Random random;
    private int previouHandVal = 0;
    private int currentHandVal = 0;
    private int[][] history = {{1,1,1},{1,1,1},{1,1,1}};

    public ProbStrategy() {
        this.random = new Random(System.currentTimeMillis());
    }



    @Override
    public Hand nextHand() {
        return null;
    }

    @Override
    public void stuty(boolean win) {

    }
}
