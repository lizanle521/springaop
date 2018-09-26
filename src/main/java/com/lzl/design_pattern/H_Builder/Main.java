package com.lzl.design_pattern.H_Builder;

import com.lzl.design_pattern.H_Builder.classes.Director;
import com.lzl.design_pattern.H_Builder.classes.HtmlBuilder;
import com.lzl.design_pattern.H_Builder.classes.TextBuilder;

public class Main {
    public static void main(String[] args) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        Director director = new Director(htmlBuilder);
        director.build();
        System.out.println(htmlBuilder.getResult());

        TextBuilder textBuilder = new TextBuilder();
        Director director1 = new Director(textBuilder);
        director1.build();
        System.out.println(textBuilder.getResult());
    }
}
