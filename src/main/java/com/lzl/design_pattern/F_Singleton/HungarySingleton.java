package com.lzl.design_pattern.F_Singleton;

public class HungarySingleton {
    private static HungarySingleton singleton = new HungarySingleton();
    private HungarySingleton(){}
    public static HungarySingleton getInstance(){
        return singleton;
    }
}
