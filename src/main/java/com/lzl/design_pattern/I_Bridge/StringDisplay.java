package com.lzl.design_pattern.I_Bridge;

public class StringDisplay extends AbstractDisplay {
    @Override
    public void open() {
        System.out.println("String open");
    }

    @Override
    public void print() {
        System.out.println("String print");
    }

    @Override
    public void close() {
        System.out.println("String close");
    }
}
