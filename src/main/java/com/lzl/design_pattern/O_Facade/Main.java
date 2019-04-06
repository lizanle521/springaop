package com.lzl.design_pattern.O_Facade;

import com.lzl.design_pattern.O_Facade.pagemaker.PageMaker;

/**
 * @author lizanle
 * @data 2019/4/6 2:12 PM
 */
public class Main {
    public static void main(String[] args) {
        PageMaker.makeWelComePage("aa@bb.com","welcome.html");
    }
}
