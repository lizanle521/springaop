package com.lzl.design_pattern.M_Visitor;

/**
 * @author lizanle
 * @data 2019/4/3 9:55 PM
 */
public interface Element {
    void accept(Visitor v);
}
