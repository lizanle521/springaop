package com.lzl.design_pattern.J_Strategy.classes;

import com.lzl.design_pattern.J_Strategy.Interfaces.Strategy;

import java.util.Random;

public class ProbStrategy implements Strategy {
    private Random random;
    private int previouHandVal = 0;
    private int currentHandVal = 0;
    private int[][] history = {{1,1,1},{1,1,1},{1,1,1}};

    public ProbStrategy(int seed) {
        this.random = new Random(seed);
    }



    @Override
    public Hand nextHand() {
        int bet = random.nextInt(getSum(currentHandVal));
        int handval = 0;
        if(bet < history[currentHandVal][0]){
            handval = 0;
        }else if(bet < history[currentHandVal][0]+history[currentHandVal][1]){
            handval = 1;
        }else{
            handval = 2;
        }
        previouHandVal = currentHandVal;
        currentHandVal = handval;
        return Hand.getHand(handval);
    }

    @Override
    public void stuty(boolean win) {
        if(win){
            history[previouHandVal][currentHandVal]++;
        }else{
            history[previouHandVal][(currentHandVal+1)%3]++;
            history[previouHandVal][(currentHandVal+2)%3]++;

        }
    }

    private int getSum(int hv){
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += history[hv][i];
        }
        return sum;
    }

}
