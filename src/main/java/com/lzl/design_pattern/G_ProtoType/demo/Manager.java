package com.lzl.design_pattern.G_ProtoType.demo;

import com.lzl.design_pattern.G_ProtoType.interfaces.Product;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    private Map<String,Product> showCase = new HashMap<>();
    public Product create(String s){
        Product product = showCase.get(s);
        return product.createclone();
    }

    public void register(String s,Product product){
        showCase.put(s,product);
    }
}
