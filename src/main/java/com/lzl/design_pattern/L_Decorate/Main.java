package com.lzl.design_pattern.L_Decorate;

/**
 * @author lizanle
 * @data 2019/4/1 10:05 PM
 */
public class Main {
    public static void main(String[] args) {
        Display d1 = new StringDisplay("hello,world");
        Display sideBorder = new SideBorder(d1, '*');
        Display fullBorder = new FullBorder(d1);
        sideBorder.show();
        fullBorder.show();

        Display sb = new SideBorder(
                new FullBorder(
                        new SideBorder(
                                new FullBorder(
                                        new FullBorder(
                                                new FullBorder(d1)
                                        )
                                ), '@'
                        )
                ), '&');
        sb.show();
    }
}
