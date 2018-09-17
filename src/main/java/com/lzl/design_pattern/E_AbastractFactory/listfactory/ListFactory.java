package com.lzl.design_pattern.E_AbastractFactory.listfactory;

import com.lzl.design_pattern.E_AbastractFactory.factory.Factory;
import com.lzl.design_pattern.E_AbastractFactory.factory.Link;
import com.lzl.design_pattern.E_AbastractFactory.factory.Page;
import com.lzl.design_pattern.E_AbastractFactory.factory.Tray;

public class ListFactory extends Factory {
    @Override
    public Link createLink(String caption, String url) {
        return new ListLink(caption,url);
    }

    @Override
    public Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    public Page createPage(String author, String title) {
        return new ListPage(author,title);
    }
}
