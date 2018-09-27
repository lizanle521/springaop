package com.lzl.design_pattern.I_Bridge;

/**
 * 父类 ，功能实际实现都依赖于被委托的 抽象类
 *
 */
public class DisplayDelegate {
    private AbstractDisplay abstractDisplay;

    public DisplayDelegate(AbstractDisplay abstractDisplay) {
        this.abstractDisplay = abstractDisplay;
    }
    public void open() {
        abstractDisplay.open();
    }

    public void print() {
        abstractDisplay.print();
    }

    public void close() {
        abstractDisplay.close();
    }
}
