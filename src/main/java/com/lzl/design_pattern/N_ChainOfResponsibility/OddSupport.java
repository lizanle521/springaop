package com.lzl.design_pattern.N_ChainOfResponsibility;

/**
 * @author lizanle
 * @data 2019/4/4 3:44 PM
 */
public class OddSupport extends Support {
    private int number;

    public OddSupport(String name, int number) {
        super(name);
        this.number = number;
    }

    @Override
    protected boolean resolve(Trouble trouble) {
       if(trouble.getNumber() % 2 == 1){
           return true;
       }
        return false;
    }
}
