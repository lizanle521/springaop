package com.lzl.design_pattern.B_Adapter.object_adapter;

import com.lzl.design_pattern.B_Adapter.class_adapter.classes.Banner;
import com.lzl.design_pattern.B_Adapter.class_adapter.interfaces.Print;

public class PrintBanner implements Print {
    private Banner banner;
    public PrintBanner(Banner banner){
        this.banner = banner;
    }
    @Override
    public void printWeak() {
        banner.showWithParen();
    }

    @Override
    public void printStrong() {
        banner.showWithAster();
    }
}
