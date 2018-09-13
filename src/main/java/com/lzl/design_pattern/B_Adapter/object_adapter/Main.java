package com.lzl.design_pattern.B_Adapter.object_adapter;

import com.lzl.design_pattern.B_Adapter.class_adapter.classes.Banner;

public class Main {
    public static void main(String[] args) {
        PrintBanner printBanner = new PrintBanner(new Banner("aa"));
        printBanner.printStrong();
        printBanner.printWeak();
    }
}
