package com.lzl.design_pattern.D_Factory;

import com.lzl.design_pattern.D_Factory.framework.Factory;
import com.lzl.design_pattern.D_Factory.framework.Product;
import com.lzl.design_pattern.D_Factory.idcard.IDCardFactory;

public class Main {
    public static void main(String[] args) {
        Factory idCardFactory = new IDCardFactory();
        Product product = idCardFactory.create("lzl");
        product.use();
        Product product1 = idCardFactory.create("zz");
        product1.use();
    }
}
