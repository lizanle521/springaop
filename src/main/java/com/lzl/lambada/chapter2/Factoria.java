package com.lzl.lambada.chapter2;

import java.util.function.IntUnaryOperator;

public class Factoria {
    public IntUnaryOperator fact ;
    public Factoria(){
        fact = i -> i == 0 ? 1 : i*fact.applyAsInt(i-1);
    }

    public static void main(String[] args) {
        Factoria factoria = new Factoria();
        System.out.println(factoria.fact.applyAsInt(10));
    }
}
