package com.lzl.design_pattern.N_ChainOfResponsibility;

/**
 * @author lizanle
 * @data 2019/4/4 3:44 PM
 */
public class LimitSupport extends Support {
    private int number;

    public LimitSupport(String name, int number) {
        super(name);
        this.number = number;
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        if(number < trouble.getNumber())
        {
            return true;
        }
        return false;
    }
}
