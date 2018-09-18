package com.lzl.bytecode.chapter1;

public class OneFiledClass {
    public int a;
}
/**
 "C:\Program Files\Java\jdk1.8.0_73\bin\javap.exe" -v -l -p -s -c com.lzl.bytecode.chapter1.OneFiledClass
 Classfile /F:/ideaworkspace/springaop/target/classes/com/lzl/bytecode/chapter1/OneFiledClass.class
 Last modified 2018-9-18; size 332 bytes
 MD5 checksum 7be390c4d4ccbd4062b9514054ff76ad
 Compiled from "OneFiledClass.java"
 public class com.lzl.bytecode.chapter1.OneFiledClass
 minor version: 0
 major version: 52
 flags: ACC_PUBLIC, ACC_SUPER
 Constant pool:
 #1 = Methodref          #3.#15         // java/lang/Object."<init>":()V
 #2 = Class              #16            // com/lzl/bytecode/chapter1/OneFiledClass
 #3 = Class              #17            // java/lang/Object
 #4 = Utf8               a
 #5 = Utf8               I
 #6 = Utf8               <init>
 #7 = Utf8               ()V
 #8 = Utf8               Code
 #9 = Utf8               LineNumberTable
 #10 = Utf8               LocalVariableTable
 #11 = Utf8               this
 #12 = Utf8               Lcom/lzl/bytecode/chapter1/OneFiledClass;
 #13 = Utf8               SourceFile
 #14 = Utf8               OneFiledClass.java
 #15 = NameAndType        #6:#7          // "<init>":()V
 #16 = Utf8               com/lzl/bytecode/chapter1/OneFiledClass
 #17 = Utf8               java/lang/Object
 {
 public int a;
 descriptor: I
 flags: ACC_PUBLIC

 public com.lzl.bytecode.chapter1.OneFiledClass();
 descriptor: ()V
 flags: ACC_PUBLIC
 Code:
 stack=1, locals=1, args_size=1
 0: aload_0
 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 4: return
 LineNumberTable:
 line 3: 0
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0       5     0  this   Lcom/lzl/bytecode/chapter1/OneFiledClass;
 }
 SourceFile: "OneFiledClass.java"
 */
