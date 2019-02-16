package com.lzl.bytecode.chapter1;

public class SingleMethodClass {
    public int a = 1;
    public int test(){
        System.out.println("111");
        return a;
    }
}
/**
 "C:\Program Files\Java\jdk1.8.0_73\bin\javap.exe" -v -l -p -s -c com.lzl.bytecode.nettyinaction.SingleMethodClass
 Classfile /F:/ideaworkspace/springaop/target/classes/com/lzl/bytecode/nettyinaction/SingleMethodClass.class
 Last modified 2018-9-18; size 589 bytes
 MD5 checksum cdbeb18e29387e8b6726c9d7a53a0262
 Compiled from "SingleMethodClass.java"
 public class com.lzl.bytecode.nettyinaction.SingleMethodClass
 minor version: 0
 major version: 52
 flags: ACC_PUBLIC, ACC_SUPER
 Constant pool:
 #1 = Methodref          #7.#21         // java/lang/Object."<init>":()V
 #2 = Fieldref           #6.#22         // com/lzl/bytecode/nettyinaction/SingleMethodClass.a:I
 #3 = Fieldref           #23.#24        // java/lang/System.out:Ljava/io/PrintStream;
 #4 = String             #25            // 111
 #5 = Methodref          #26.#27        // java/io/PrintStream.println:(Ljava/lang/String;)V
 #6 = Class              #28            // com/lzl/bytecode/nettyinaction/SingleMethodClass
 #7 = Class              #29            // java/lang/Object
 #8 = Utf8               a
 #9 = Utf8               I
 #10 = Utf8               <init>
 #11 = Utf8               ()V
 #12 = Utf8               Code
 #13 = Utf8               LineNumberTable
 #14 = Utf8               LocalVariableTable
 #15 = Utf8               this
 #16 = Utf8               Lcom/lzl/bytecode/nettyinaction/SingleMethodClass;
 #17 = Utf8               test
 #18 = Utf8               ()I
 #19 = Utf8               SourceFile
 #20 = Utf8               SingleMethodClass.java
 #21 = NameAndType        #10:#11        // "<init>":()V
 #22 = NameAndType        #8:#9          // a:I
 #23 = Class              #30            // java/lang/System
 #24 = NameAndType        #31:#32        // out:Ljava/io/PrintStream;
 #25 = Utf8               111
 #26 = Class              #33            // java/io/PrintStream
 #27 = NameAndType        #34:#35        // println:(Ljava/lang/String;)V
 #28 = Utf8               com/lzl/bytecode/nettyinaction/SingleMethodClass
 #29 = Utf8               java/lang/Object
 #30 = Utf8               java/lang/System
 #31 = Utf8               out
 #32 = Utf8               Ljava/io/PrintStream;
 #33 = Utf8               java/io/PrintStream
 #34 = Utf8               println
 #35 = Utf8               (Ljava/lang/String;)V
 {
 public int a;
 descriptor: I
 flags: ACC_PUBLIC

 public com.lzl.bytecode.nettyinaction.SingleMethodClass();
 descriptor: ()V
 flags: ACC_PUBLIC
 Code:
 stack=2, locals=1, args_size=1
 0: aload_0
 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 4: aload_0
 5: iconst_1
 6: putfield      #2                  // Field a:I
 9: return
 LineNumberTable:
 line 3: 0
 line 4: 4
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0      10     0  this   Lcom/lzl/bytecode/nettyinaction/SingleMethodClass;

 public int test();
 descriptor: ()I
 flags: ACC_PUBLIC
 Code:
 stack=2, locals=1, args_size=1
 0: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
 3: ldc           #4                  // String 111
 5: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 8: aload_0
 9: getfield      #2                  // Field a:I
 12: ireturn
 LineNumberTable:
 line 6: 0
 line 7: 8
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0      13     0  this   Lcom/lzl/bytecode/nettyinaction/SingleMethodClass;
 }
 SourceFile: "SingleMethodClass.java"
 **/