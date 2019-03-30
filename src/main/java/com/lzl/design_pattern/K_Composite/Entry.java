package com.lzl.design_pattern.K_Composite;

/**
 * @author lizanle
 * @data 2019/3/30 1:56 PM
 */
public abstract class Entry {
    public abstract String getName();
    public abstract int getSize();

    public void printList(){
        printList("");
    }
    
    public abstract void printList(String prefix);

    /**
     * 子类可能没有的方法 父类就不要定义为抽象方法，定义一个会抛异常的方法
     * 有这个方法的子类自己去覆盖，没有的子类 调用就会报错
     * @param entry
     * @throws FileNoAddMethodException
     */
    public void add(Entry entry) throws FileNoAddMethodException{
        throw new FileNoAddMethodException("");
    }

    @Override
    public String toString() {
        return getName() + "------" + getSize();
    }
}
