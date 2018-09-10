package com.lzl.java.basic;

import org.junit.Test;

/**
 * Created by Lizanle on 2018/3/5.
 */
public class BreakDemo {
    @Test
    public void testBreak(){
        a:{
            System.out.println("0");
            b:{
                System.out.println("1");
                c:{
                    System.out.println("2");
                    if ( 1 == 1 ) {
                        break a;
                    }
                }
                System.out.println("3");
            }
            System.out.println("4");
        }
        System.out.println("5");
    }
}
