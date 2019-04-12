package com.lzl.design_pattern.R_Memento;

/**
 * @author lizanle
 * @data 2019/4/12 10:43 PM
 */
public class Main {
    public static void main(String[] args) {
        Gamer gamer = new Gamer(100);
        Memento memento = gamer.createMemento();
        for (int i = 0; i < 100; i++) {
            System.out.println("====="+i);
            System.out.println("当前状态："+gamer);

            gamer.bet();
            System.out.println("所持金钱为"+gamer.getMoney()+"");
            if(gamer.getMoney() > memento.getMoney()){
                System.out.println("所持金钱增加许多，保持当前游戏状态");
                memento = gamer.createMemento();
            }else if(gamer.getMoney() < 10){
                gamer.restoreMemento(memento);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("");
        }
    }
}
