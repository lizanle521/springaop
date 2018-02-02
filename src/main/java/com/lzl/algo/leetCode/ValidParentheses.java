package com.lzl.algo.leetCode;

import org.junit.Test;

/**
 * 合法的括号判断
 * 用栈
 * Created by Lizanle on 2018/1/31.
 */
public class ValidParentheses {
    public boolean isValid(String s){
        java.util.Stack<Character> stack = new java.util.Stack<Character>();
        stack.push('#');
        String demo = "(){}[]";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!isPair(stack.peek(), c)){
                stack.push(c);
            }else {
                stack.pop();
            }
        }
        if(stack.pop() != '#'){
            return false;
        }else {
            return true;
        }
    }

    public boolean isPair(char a ,char b){
        String pairA = "()";
        String pairB = "[]";
        String pairC = "{}";
        if((pairA.indexOf(a) >= 0 && pairA.indexOf(b) >0) || (pairB.indexOf(a) >= 0 && pairB.indexOf(b) >0) || (pairC.indexOf(a) >= 0 && pairC.indexOf(b) >0)){
            return true;
        }else{
            return false;
        }
    }


    @Test
    public void test(){
        boolean valid = isValid("()");
        System.out.println(valid);
    }

    @Test
    public void test1(){
        boolean valid = isValid("){");
        System.out.println(valid);
    }

    @Test
    public void test2(){
        boolean valid = isValid("([]");
        System.out.println(valid);
    }
}
