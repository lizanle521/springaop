package com.lzl.algo.leetCode;

import org.junit.Test;

/**
 * 合法的括号判断
 * 用栈
 * Created by Lizanle on 2018/1/31.
 */
public class ValidParentheses {
    public boolean isValid(String s){
        Stack stack = new Stack();
        String demo = "(){}[]";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!isPair(stack.pop(), c)){
                stack.push(new Node(c));
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
        if((pairA.indexOf(a) >= 0 && pairA.indexOf(b) >=0) || (pairB.indexOf(a) >= 0 && pairB.indexOf(b) >=0) || (pairC.indexOf(a) >= 0 && pairC.indexOf(b) >=0)){
            return true;
        }else{
            return false;
        }
    }

    class Stack{
        private Node top;
        private Node button;

        public Stack() {

        }

        public Node getTop() {
            return top;
        }

        public void setTop(Node top) {
            this.top = top;
        }

        public Node getButton() {
            return button;
        }

        public void setButton(Node button) {
            this.button = button;
        }

        public char pop(){
            if(top == null && button == null){
                return '#';
            }
            char pop = top.currentVal;
            top = top.getPre();
            if (top != null && top.getPre() != null) {
                    top.getPre().setNext(null);
            }
            return pop;
        }

        public void push(Node node){
            if(top  ==  null && button == null){
                top = node;
                button = node;
                top.setPre(button);
                return ;
            }
            if(top != null) {
                top.next = node;
                node.setPre(top);
                top = node;
            }else {
                top = node ;
                button.next = top;
                top.pre = button;
            }
        }
    }

    class Node{
        private Node next;
        private Node pre;
        private char currentVal;

        public Node(char currentVal) {
            this.currentVal = currentVal;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public char getCurrentVal() {
            return currentVal;
        }

        public void setCurrentVal(char currentVal) {
            this.currentVal = currentVal;
        }

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
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
