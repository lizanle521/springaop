package com.lzl.design_pattern.R_Memento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author lizanle
 * @data 2019/4/12 10:17 PM
 */
public class Gamer {
    // 玩家所持有的金钱
    private int money;
    // 获得的水果
    private List fruits = new ArrayList<>();

    private Random random = new Random();

    private static String[] fruitsName = {"苹果","葡萄","香蕉","橘子"};

    public Gamer(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void bet(){
        // 扔骰子
        int dice = random.nextInt(6) + 1;

        if(dice == 1){
            money += 100;
            System.out.println("所持有的金钱增加了");
        } else if(dice == 2){
            money /= 2;
            System.out.println("所持金钱简单。");
        }else if(dice == 6){
            String fruit = getFruit();
            System.out.println("获得了水果（"+fruit + ").");
            fruits.add(fruit);
        }else {
            System.out.println("什么都没发生");
        }
    }

    public Memento createMemento(){
        Memento memento = new Memento(money);
        Iterator it = fruits.iterator();
        while (it.hasNext()){
            String next = (String) it.next();
            if(next.startsWith("好吃的")){
                memento.addFruits(next);
            }
        }
        return memento;
    }

    public void restoreMemento(Memento memento){
        this.money = memento.money;
        this.fruits = memento.getFruits();
    }

    @Override
    public String toString() {
        return "Gamer{" +
                "money=" + money +
                ", fruits=" + fruits +
                '}';
    }

    private String getFruit() {
        String prefix = "";
        if(random.nextBoolean()){
            prefix = "好吃的";
        }
        return  prefix + fruitsName[random.nextInt(fruitsName.length)];
    }
}
