package com.lzl.lambada.chapter5;

public class DisplayLine {
    final int disp;
    final String line;

    public DisplayLine(int disp, String line) {
        this.disp = disp;
        this.line = line;
    }

    @Override
    public String toString() {
        return "DisplayLine{" +
                "disp=" + disp +
                ", line='" + line + '\'' +
                '}';
    }
}
