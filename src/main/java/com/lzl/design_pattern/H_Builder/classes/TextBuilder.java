package com.lzl.design_pattern.H_Builder.classes;

import com.lzl.design_pattern.H_Builder.interfaces.Builder;

public class TextBuilder extends Builder {

    private StringBuffer sb = new StringBuffer();

    @Override
    public void makeTitle(String title) {
        sb.append("======================\n");
        sb.append("["+title+"]\n");
        sb.append("\n");
    }

    @Override
    public void makeString(String str) {
        sb.append("*"+str+"\n");
        sb.append("\n");
    }

    @Override
    public void makeItems(String[] items) {
        for (String item : items) {
            sb.append("  "+ item+"\n");
        }
        sb.append("\n");
    }

    @Override
    public void close() {
        sb.append("===================\n");
    }

    public String getResult(){
        return sb.toString();
    }
}
