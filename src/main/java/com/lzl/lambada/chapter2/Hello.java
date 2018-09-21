package com.lzl.lambada.chapter2;

public class Hello {
    Runnable r1 = ()->{
        System.out.println(this);
    };

    Runnable r2 = ()->{
        System.out.println(toString());
    };

    @Override
    public String toString() {
        return "hello world";
    }

    public static void main(String[] args) {
        new Hello().r1.run();
        new Hello().r2.run();
    }
}
