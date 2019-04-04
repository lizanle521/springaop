package com.lzl.design_pattern.N_ChainOfResponsibility;

/**
 * @author lizanle
 * @data 2019/4/4 3:25 PM
 */
public abstract class Support {
    private String name;
    private Support next;

    public Support(String name) {
        this.name = name;
    }

    public Support setNext(Support next){
        this.next = next;
        return next;
    }

    public final void support(Trouble trouble){
        if(resolve(trouble)){
            done(trouble);
        }else if(next != null){
            next.support(trouble);
        }else {
            fail(trouble);
        }
    }

    protected  void fail(Trouble trouble){
        System.out.println(trouble + "faild by "+this);
    }

    protected  void done(Trouble trouble){
        System.out.println(trouble + "done by "+this);
    }

    @Override
    public String toString() {
        return name;
    }

    protected abstract boolean resolve(Trouble trouble);
}
