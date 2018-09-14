package com.lzl.design_pattern.C_Template;

import com.alibaba.druid.sql.visitor.functions.Char;

public class Main {
    public static void main(String[] args) {
        AbstractDisplay c = new CharDisplay('c');
        AbstractDisplay test = new StringDisplay("test");
        c.display();
        test.display();
    }
}
