package com.lzl.design_pattern.G_ProtoType.demo.product;

import com.lzl.design_pattern.G_ProtoType.interfaces.Product;

public class BoxOpen extends Product {
    public BoxOpen(String s) {
        this.s = s;
    }

    private String s;

    @Override
    public void use(String s) {
        System.out.println(s);
    }
}
