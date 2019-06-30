package com.lzl.design_pattern.W_Interpreter;

/**
 * @author lizanle
 * @Date 2019/4/19 16:21
 */
public class CommandNode extends Node {
    private Node node;
    @Override
    public void parse(Context context) {
        if(context.getCurrentToken().equals("repeat")){
            node  = new RepeatCommandNode();
            node.parse(context);
        }else{
            node = new PrimitiveCommandNode();
            node.parse(context);
        }

    }

    @Override
    public String toString() {
        return node.toString();
    }
}
