package com.lzl.design_pattern.D_Factory.framework;

public abstract class Factory {
    public Product create(String owner){
        Product product = createProduct(owner);
        registProduct(product);
        return product;
    }

    public abstract Product createProduct(String owner);
    public abstract void registProduct(Product product);
}
