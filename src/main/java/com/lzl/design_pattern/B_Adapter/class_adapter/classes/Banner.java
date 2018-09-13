package com.lzl.design_pattern.B_Adapter.class_adapter.classes;

public class Banner {
    private String str;

    public Banner(String str) {
        this.str = str;
    }

    public void showWithParen() {
        System.out.println("{"+str+"}");
    }

    public void showWithAster(){
        System.out.println("*"+str+"*");
    }
}
