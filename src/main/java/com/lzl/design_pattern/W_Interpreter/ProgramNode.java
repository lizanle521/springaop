package com.lzl.design_pattern.W_Interpreter;

/**
 * @author lizanle
 * @Date 2019/4/19 16:12
 */
public class ProgramNode extends Node {
    private Node commandListNode;
    @Override
    public void parse(Context context) {
        context.skipToken("program");
        commandListNode = new CommandListNode();
        commandListNode.parse(context);
    }

    @Override
    public String toString() {
        return "[program" +commandListNode + "]";
    }
}
