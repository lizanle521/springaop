package com.lzl.design_pattern.R_Memento;

import java.util.ArrayList;

/**
 * @author lizanle
 * @data 2019/4/12 10:09 PM
 */
public class Memento {
    int money;
    ArrayList<String> fruits;

    /**
     * 窄接口，操作的信息有限
     * @return
     */
    public int getMoney() {
        return money;
    }

    Memento(int money) {
        this.money = money;
        fruits = new ArrayList<>();
    }

    void addFruits(String fruit){
        fruits.add(fruit);
    }

    ArrayList<String> getFruits() {
        return fruits;
    }
}
