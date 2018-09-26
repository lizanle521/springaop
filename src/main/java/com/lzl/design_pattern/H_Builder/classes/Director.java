package com.lzl.design_pattern.H_Builder.classes;

import com.lzl.design_pattern.H_Builder.interfaces.Builder;

public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void build(){
        builder.makeTitle("test");
        builder.makeItems(new String[]{"item1","item2"});
        builder.makeString("i'm a robot");
        builder.close();
    }
}
