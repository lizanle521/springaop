package com.lzl.design_pattern.L_Decorate;

/**
 * @author lizanle
 * @data 2019/4/1 9:23 PM
 */
public abstract class Border extends Display {
     protected Display display;

    public Border(Display display) {
        this.display = display;
    }

}
