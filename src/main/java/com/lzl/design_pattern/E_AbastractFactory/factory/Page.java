package com.lzl.design_pattern.E_AbastractFactory.factory;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Page {
    protected String author;
    protected String title;
    protected List<Item> content = new ArrayList<>();

    public Page(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public void add(Item item) {
        content.add(item);
    }

    public void output(){
        try {
            String fileNmae = title+".html";
            FileWriter fileWriter = new FileWriter(fileNmae);
            fileWriter.write(this.makeHtml());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String makeHtml();
}
