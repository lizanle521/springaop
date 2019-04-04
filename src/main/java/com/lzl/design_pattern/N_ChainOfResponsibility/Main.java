package com.lzl.design_pattern.N_ChainOfResponsibility;

/**
 * @author lizanle
 * @data 2019/4/4 4:00 PM
 */
public class Main {
    public static void main(String[] args) {
        Support alice = new SpecialSupport("alice", -1);
        Support bob = new OddSupport("bob", 21);
        Support kk = new SpecialSupport("kk", 1);
        Support limitSupport = new LimitSupport("limi", 0);
        Support gg = new NoSupport("gg", -1);
        Support qq = new SpecialSupport("qq", 40);
        alice.setNext(bob).setNext(kk).setNext(limitSupport).setNext(gg).setNext(qq);
        for (int i = 0; i < 100; i++) {
            alice.support(new Trouble(i));
        }
    }
}
