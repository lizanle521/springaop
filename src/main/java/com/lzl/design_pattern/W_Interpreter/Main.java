package com.lzl.design_pattern.W_Interpreter;

/**
 * @author lizanle
 * @Date 2019/4/19 16:48
 */
public class Main {
    public static void main(String[] args) {
        String text="program repeat 4 repeat 3 go right go left end  right end end";
        Node node=new ProgramNode();
        node.parse(new Context(text));
        System.out.println(node.toString());
    }
}
