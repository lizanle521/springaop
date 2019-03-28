package com.lzl.design_pattern.J_Strategy.classes;

/**
 * @author lizanle
 * @data 2019/3/28 9:02 PM
 */
public class Main {
    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println("Usage:java main randomseed1 randomseed2");
            System.out.println("Example:java main 314 315");
            System.exit(1);
        }

        int seed1 = Integer.parseInt(args[0]);
        int seed2 = Integer.parseInt(args[1]);
        Player t1 = new Player("T1", new WinningStrategy(seed1));
        Player t2 = new Player("T2", new ProbStrategy(seed2));

        for (int i = 0; i < 10000; i++) {
            Hand h1 = t1.nextHand();
            Hand h2 = t2.nextHand();

            if(h1.isStrongerThan(h2)){
                System.out.println("winner :"+t1);
                t1.win();
                t2.lose();
            }else if(h2.isStrongerThan(h1)){
                System.out.println("winner :"+t2);
                t1.lose();
                t2.win();
            }else{
                System.out.println("even");
                t1.even();
                t2.even();
            }
        }

        System.out.println(t1.toString());
        System.out.println(t2.toString());
    }
}
