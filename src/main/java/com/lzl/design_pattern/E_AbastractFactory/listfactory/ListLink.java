package com.lzl.design_pattern.E_AbastractFactory.listfactory;

import com.lzl.design_pattern.E_AbastractFactory.factory.Link;

public class ListLink extends Link {
    public ListLink(String caption, String url) {
        super(caption, url);
    }

    @Override
    public String makeHtml() {
        return "<li><a href=\""+url+"\">"+caption+"</a></li>";
    }
}
