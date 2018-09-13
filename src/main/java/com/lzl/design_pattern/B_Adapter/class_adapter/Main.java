package com.lzl.design_pattern.B_Adapter.class_adapter;

import com.lzl.design_pattern.B_Adapter.class_adapter.classes.PrintBanner;
import com.lzl.design_pattern.B_Adapter.class_adapter.interfaces.Print;

public class Main {
    public static void main(String[] args) {
        Print print = new PrintBanner("aaa");
        print.printStrong();
        print.printWeak();
    }
}
