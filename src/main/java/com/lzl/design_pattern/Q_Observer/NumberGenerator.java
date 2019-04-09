package com.lzl.design_pattern.Q_Observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lizanle
 * @data 2019/4/9 11:08 PM
 */
public abstract class NumberGenerator {
    private List<Observer> list = new ArrayList<>();

    public void addObserver(Observer observer){
        list.add(observer);
    }

    public void delObserver(Observer observer){
        list.remove(observer);
    }

    public void notifyObserver(){
        Iterator<Observer> iterator = list.iterator();
        while (iterator.hasNext()){
            Observer next = iterator.next();
            next.update(this);
        }
    }

    /**
     * 生成数值
     * @return
     */
    public abstract int getNumber();

    /**
     * 生成数值
     */
    public abstract void execute();
}
