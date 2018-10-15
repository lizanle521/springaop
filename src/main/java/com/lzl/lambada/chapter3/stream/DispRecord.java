package com.lzl.lambada.chapter3.stream;

import com.lzl.lambada.chapter3.Book;

import java.util.stream.IntStream;

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

    public DispRecord(Book book){
        this(book.getTitle(),0, IntStream.of(book.getPageCounts()).sum());
    }

    int totalDisp(){
        return disp+lenght;
    }
}
