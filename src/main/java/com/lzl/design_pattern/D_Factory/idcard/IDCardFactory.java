package com.lzl.design_pattern.D_Factory.idcard;

import com.lzl.design_pattern.D_Factory.framework.Factory;
import com.lzl.design_pattern.D_Factory.framework.Product;

import java.util.ArrayList;
import java.util.List;

public class IDCardFactory extends Factory {
    private List<Product> owners = new ArrayList<>();

    @Override
    public Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    public void registProduct(Product product) {
        owners.add(product);
    }
}
