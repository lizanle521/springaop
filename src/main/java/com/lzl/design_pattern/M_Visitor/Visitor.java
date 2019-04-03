package com.lzl.design_pattern.M_Visitor;

/**
 * @author lizanle
 * @data 2019/4/3 9:56 PM
 */
public abstract class Visitor {
    public abstract void visit(File file);
    public abstract void visit(Directory directory);
}
