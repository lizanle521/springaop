package com.lzl.design_pattern.M_Visitor;

import java.util.Iterator;

/**
 * @author lizanle
 * @data 2019/4/3 10:13 PM
 */
public class ListVisitor extends Visitor {
    private String currentDir = "";
    @Override
    public void visit(File file) {
        System.out.println(currentDir + "/"+file);
    }

    @Override
    public void visit(Directory directory) {
        System.out.println(currentDir + "/" + directory);
        String saveDir = currentDir;
        currentDir = currentDir + "/" + directory.getName();

        Iterator iterator = directory.iterator();
        while (iterator.hasNext()){
            Entry next = (Entry) iterator.next();
            next.accept(this);
        }
        currentDir = saveDir;
    }
}
