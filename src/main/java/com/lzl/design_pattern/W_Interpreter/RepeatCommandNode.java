package com.lzl.design_pattern.W_Interpreter;

/**
 * @author lizanle
 * @Date 2019/4/19 16:22
 */
public class RepeatCommandNode extends Node {
    private int number;
    private Node commandListNode;

    @Override
    public void parse(Context context) {
        context.skipToken("repeat");
        number=context.currentNumber();
        context.nextToken();
        commandListNode = new CommandListNode();
        commandListNode.parse(context);
    }

    @Override
    public String toString() {
        return "repeat " + number + " " + commandListNode;
    }
}
