package com.lzl.design_pattern.E_AbastractFactory;

import com.lzl.design_pattern.E_AbastractFactory.factory.Factory;
import com.lzl.design_pattern.E_AbastractFactory.factory.Link;
import com.lzl.design_pattern.E_AbastractFactory.factory.Page;
import com.lzl.design_pattern.E_AbastractFactory.factory.Tray;
import com.lzl.design_pattern.E_AbastractFactory.listfactory.ListFactory;

public class Main {
    public static void main(String[] args) {
        Factory factory = Factory.getFactory(ListFactory.class.getName());
        Link jrtt = factory.createLink("今日头条", "http://jinritoutiao.com");
        Link bd = factory.createLink("百度", "http://baidu.com");
        Link gg = factory.createLink("谷歌", "http://google.com");

        Tray tray = factory.createTray("互联网");
        tray.add(jrtt);
        tray.add(bd);
        tray.add(gg);

        Page page = factory.createPage("lzl", "互联网公司");
        page.add(tray);
        page.output();

    }
}
