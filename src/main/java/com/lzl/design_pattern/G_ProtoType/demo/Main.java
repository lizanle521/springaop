package com.lzl.design_pattern.G_ProtoType.demo;

import com.lzl.design_pattern.G_ProtoType.demo.product.BoxOpen;
import com.lzl.design_pattern.G_ProtoType.demo.product.UnderlineBox;
import com.lzl.design_pattern.G_ProtoType.interfaces.Product;

public class Main {
    public static void main(String[] args) {
        BoxOpen boxOpen = new BoxOpen("AAA");
        UnderlineBox underlineBox = new UnderlineBox("BBB");
        Manager manager = new Manager();
        manager.register("AAA",boxOpen);
        manager.register("BBB",underlineBox);

        Product newproduct = manager.create("AAA");
        newproduct.use("ss");

        Product newProduct1 = manager.create("BBB");
        newProduct1.use("bb");
    }
}
