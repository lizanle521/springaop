package com.lzl.design_pattern.D_Factory.idcard;

import com.lzl.design_pattern.D_Factory.framework.Product;

public class IDCard extends Product {
    private String owner;

    public IDCard(String owner) {
        this.owner = owner;
    }

    @Override
    public void use() {
        System.out.println("使用"+owner+"的身份证");
    }

    @Override
    public String getOwner() {
        return owner;
    }
}
