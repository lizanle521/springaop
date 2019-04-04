package com.lzl.design_pattern.N_ChainOfResponsibility;

/**
 * @author lizanle
 * @data 2019/4/4 3:32 PM
 */
public class Trouble {
    private int number;

    public Trouble(int number) {
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    @Override
    public String toString() {
        return "[Trouble "+number+"]";
    }
}
