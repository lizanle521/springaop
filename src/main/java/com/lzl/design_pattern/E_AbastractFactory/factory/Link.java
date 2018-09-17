package com.lzl.design_pattern.E_AbastractFactory.factory;

public abstract class Link extends Item {
    protected String url;

    public Link(String caption,String url) {
        super(caption);
        this.url = url;
    }

}
