package com.lzl.design_pattern.I_Bridge;

/**
 * 功能层次
 */
public class SubDisplayDelegate extends DisplayDelegate {

    public SubDisplayDelegate(AbstractDisplay abstractDisplay) {
        super(abstractDisplay);
    }

    public void multiDisplay(int times){
        open();
        for (int i = 0; i < times; i++) {
            print();
        }
        close();
    }
}
