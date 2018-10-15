package com.lzl.lambada.chapter3.stream;

public class DispRecord {
    /**
     * 书的名称
     */
    final String title;
    /**
     * 本书的厚度，之前的书的总厚度
     */
    final int disp,lenght;

    public DispRecord(String title, int disp, int lenght) {
        this.title = title;
        this.disp = disp;
        this.lenght = lenght;
    }

    int totalDisp(){
        return disp+lenght;
    }
}
