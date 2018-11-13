package com.lzl.shell.io;

public class FinalizeEscapeGC {
    public static FinalizeEscapeGC HOOK = null;

    @Override
    protected void finalize() throws Throwable {
        HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {

        HOOK = new FinalizeEscapeGC();
        HOOK = null;
        System.gc();
        // finalize的执行优先级很低。如果不让线程休眠一会，可能就会导致 下边的判断代码先于finalize方法
        Thread.sleep(100);
        if(HOOK == null){
            System.out.println(" i'm dead");
        }else{
            System.out.println("i'm alive");
        }


    }
}
