package com.lzl.design_pattern.W_Interpreter;

/**
 * @author lizanle
 * @Date 2019/4/19 16:22
 */
public class PrimitiveCommandNode extends Node {
    private String name;
    @Override
    public void parse(Context context) {
        name = context.getCurrentToken();
        context.skipToken(name);
        if(!name.equals("go") && !name.equals("right") && !name.equals("left")) {
            throw new RuntimeException(name + "is not defined");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
