package com.lzl.design_pattern.G_ProtoType.interfaces;

public abstract class Product implements Cloneable {
    public abstract void use(String s);
    public Product createclone()  {
        Product p = null;
        try {
            p = (Product)clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }
}
