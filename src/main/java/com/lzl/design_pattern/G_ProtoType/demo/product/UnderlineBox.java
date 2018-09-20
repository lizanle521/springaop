package com.lzl.design_pattern.G_ProtoType.demo.product;

import com.lzl.design_pattern.G_ProtoType.interfaces.Product;

public class UnderlineBox extends Product {
    private String s ;

    public UnderlineBox(String s) {
        this.s = s;
    }

    @Override
    public void use(String s) {
        System.out.println(s);
        System.out.println("-------------------");
    }
}
