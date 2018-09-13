package com.lzl.design_pattern.B_Adapter.class_adapter.classes;

import com.lzl.design_pattern.B_Adapter.class_adapter.interfaces.Print;

public class PrintBanner extends Banner implements Print {

    public PrintBanner(String str) {
        super(str);
    }

    @Override
    public void printWeak() {
        showWithParen();
    }

    @Override
    public void printStrong() {
        showWithAster();
    }
}
