package com.lzl.design_pattern.M_Visitor;

import com.lzl.design_pattern.K_Composite.FileNoAddMethodException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lizanle
 * @data 2019/4/3 10:07 PM
 */
public class Directory extends Entry {
    private String name;
    List<Entry> list = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        int size = 0;
        Iterator<Entry> iterator = list.iterator();
        while (iterator.hasNext()){
            Entry next = iterator.next();
            size += next.getSize();
        }
        return size;
    }

    @Override
    public Entry add(Entry entry)  {
        list.add(entry);
        return this;
    }

    @Override
    public Iterator iterator()  {
        return list.iterator();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
