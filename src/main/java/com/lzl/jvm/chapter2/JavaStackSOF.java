package com.lzl.jvm.chapter2;

import org.junit.Test;

/**
 * 栈溢出 -Xss128k  951
 *        -Xss256k  3221
 */
public class JavaStackSOF {
    private static int a = 1;

    public void stackLeak(){
        a++;
        stackLeak();
    }

    @Test
    public void testStackLeak() throws Throwable{
        try {
            stackLeak();
        }catch (Throwable e){
            System.out.println("stack deep length:"+a);
            throw  e;
        }
    }
}
