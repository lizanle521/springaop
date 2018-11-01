package com.lzl.java.basic;

public class StringInternDemo {
    public static void main(String[] args) {
        // 通过new 构造，直接分配在堆内存, 以及常量池中1的对象，但是此时s是指向堆中的对象的
        String s = new String("aaa");
        String s1 = "aaa";
        System.out.println(s == s1); // false

        // intern的话就分配在字符串池
        s = new String("bbb").intern();
        s1 = "bbb";
        System.out.println(s == s1); // true

        s = "ccc";
        s1 = "ccc";
        System.out.println(s == s1); //true

        s = new String("ddd").intern();
        s1 = new String("ddd").intern();
        System.out.println(s == s1); // true

        s1 = "ab" + "cd";
        s = "abcd";
        System.out.println(s == s1); // true

        String temp = "hh";
        s = "a"+temp;
        s1 = "ahh";
        System.out.println(s == s1); // false

        s = s.intern();
        System.out.println(s == s1); // true

        String s3 = new String("1") + new String("1");    // 此时生成了四个对象 常量池中的"1" + 2个堆中的"1" + s3指向的堆中的对象（注此时常量池不会生成"11"）
        s3.intern();    // jdk1.7之后，常量池不仅仅可以存储对象，还可以存储对象的引用，会直接将s3的地址存储在常量池,如果没有这行代码，那么  结果jdk1.7以后的结果也为 false
        String s4 = "11";    // jdk1.7之后，常量池中的地址其实就是s3的地址
        System.out.println(s3 == s4); // jdk1.7之前false， jdk1.7之后true

        s3 = new String("2") + new String("2");
        s4 = "22";        // 常量池中不存在22，所以会新开辟一个存储22对象的常量池地址
        s3.intern();    // 常量池22的地址和s3的地址不同
        System.out.println(s3 == s4); // false


    }
}
