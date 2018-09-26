package com.lzl.design_pattern.H_Builder.classes;

import com.lzl.design_pattern.H_Builder.interfaces.Builder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HtmlBuilder extends Builder {
    private String fileName;
    private PrintWriter printWriter;
    @Override
    public void makeTitle(String title) {
        fileName = title + ".html";
        try {
            printWriter = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.write("<html><head><title>"+title+"</title></head><body>");
        printWriter.write("<h1>"+title+"</h1>");
    }

    @Override
    public void makeString(String str) {
        printWriter.write("<p>"+str+"</p>");
    }

    @Override
    public void makeItems(String[] items) {
        printWriter.println("<ul>");
        for (String item : items) {
            printWriter.write("<li>"+item+"</li>");
        }
        printWriter.println("</ul>");

    }

    @Override
    public void close() {
        printWriter.write("</body></html>");
        printWriter.close();
    }

    public String getResult(){
        return fileName;
    }
}
