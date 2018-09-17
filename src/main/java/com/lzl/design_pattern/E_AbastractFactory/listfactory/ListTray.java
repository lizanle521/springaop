package com.lzl.design_pattern.E_AbastractFactory.listfactory;

import com.lzl.design_pattern.E_AbastractFactory.factory.Item;
import com.lzl.design_pattern.E_AbastractFactory.factory.Tray;

import java.util.Iterator;

public class ListTray extends Tray {
    public ListTray(String caption) {
        super(caption);
    }

    @Override
    public String makeHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<li>\n");
        sb.append(caption);
        sb.append("<ul>\n");
        Iterator<Item> iterator = super.tray.iterator();
        while (iterator.hasNext()){
            Item next = iterator.next();
            sb.append(next.makeHtml());
        }
        sb.append("</ul>\n");
        sb.append("</li>\n");
        return sb.toString();
    }
}
