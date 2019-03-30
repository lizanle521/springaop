package com.lzl.design_pattern.K_Composite;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lizanle
 * @data 2019/3/30 2:36 PM
 */
public class Directory extends Entry {
    private String name;
    private List<Entry> entryList = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        if(CollectionUtils.isEmpty(entryList)){
            return 0;
        }else{
            int sum = 0;
            for (Entry entry : entryList) {
                sum += entry.getSize();
            }
            return sum;
        }

    }

    @Override
    public void printList(String prefix) {
        System.out.println(prefix+"/"+this);
        Iterator<Entry> iterator = entryList.iterator();
        while (iterator.hasNext()){
            Entry next = iterator.next();
            next.printList(prefix+"---"+name);
        }
    }

    @Override
    public void add(Entry entry)  {
        entryList.add(entry);
    }
}
