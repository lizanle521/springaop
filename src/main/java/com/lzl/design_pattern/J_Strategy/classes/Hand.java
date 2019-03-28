package com.lzl.design_pattern.J_Strategy.classes;

/**
 * 手势 石头剪刀布
 */
public class Hand {
    public static final int SHI = 0;
    public static final int JIAN = 1;
    public static final int BU = 2;
    private int val;
    public static final Hand[] hands = new Hand[]{
            new Hand(JIAN),
            new Hand(SHI),
            new Hand(BU)
    };
    private final String[] name = new String[]{"石头","剪刀","布"};

    public Hand(int val) {
        this.val = val;
    }

    public static Hand getHand(int handValue){
        return hands[handValue];
    }

    public boolean isStrongerThan(Hand h){
        return fight(h)==1;
    }

    public boolean isWeakThan(Hand h){
        return fight(h) == -1;
    }

    private int fight(Hand h){
        if(h.val == this.val){
            return 0;
        }else if((h.val + 1) % 3 == this.val){
            return 1;
        }else{
            return -1;
        }
    }

    public String toString(){
        return name[val];
    }
}
