package com.lzl.design_pattern.I_Bridge;

public class CharDisplay extends AbstractDisplay {
    @Override
    public void open() {
        System.out.println("Char open");
    }

    @Override
    public void print() {
        System.out.println("char print");
    }

    @Override
    public void close() {
        System.out.println("Char close");
    }
}
