package com.lzl.design_pattern.A_Iterator.classes;

import com.lzl.design_pattern.A_Iterator.interfaces.Aggregate;

public class Book  {
    private  String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
