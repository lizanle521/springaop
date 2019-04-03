package com.lzl.design_pattern.M_Visitor;

import com.lzl.design_pattern.K_Composite.FileNoAddMethodException;

import java.util.Iterator;

/**
 * @author lizanle
 * @data 2019/4/3 9:57 PM
 */
public abstract class Entry implements Element {
    public abstract String getName();
    public abstract int getSize();

    public Entry add(Entry entry) throws FileTreatmentException{
        throw new FileTreatmentException();
    }

    public Iterator iterator() throws FileTreatmentException {
        throw new FileTreatmentException();
    }

    public String toString() {
        return getName() + "(" + getSize() + ")";
    }

}
