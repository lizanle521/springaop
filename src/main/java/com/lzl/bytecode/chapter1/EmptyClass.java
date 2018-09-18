package com.lzl.bytecode.chapter1;

public class EmptyClass {
}
/*
 "C:\Program Files\Java\jdk1.8.0_73\bin\javap.exe" -v -l -p -s -c com.lzl.bytecode.chapter1.EmptyClass
Classfile /F:/ideaworkspace/springaop/target/classes/com/lzl/bytecode/chapter1/EmptyClass.class
  Last modified 2018-9-18; size 307 bytes
  MD5 checksum 61d3ac0a1765932a15e6a15e4f124e1b
  Compiled from "EmptyClass.java"
public class com.lzl.bytecode.chapter1.EmptyClass
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #3.#13         // java/lang/Object."<init>":()V
   #2 = Class              #14            // com/lzl/bytecode/chapter1/EmptyClass
   #3 = Class              #15            // java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LineNumberTable
   #8 = Utf8               LocalVariableTable
   #9 = Utf8               this
  #10 = Utf8               Lcom/lzl/bytecode/chapter1/EmptyClass;
  #11 = Utf8               SourceFile
  #12 = Utf8               EmptyClass.java
  #13 = NameAndType        #4:#5          // "<init>":()V
  #14 = Utf8               com/lzl/bytecode/chapter1/EmptyClass
  #15 = Utf8               java/lang/Object
{
  public com.lzl.bytecode.chapter1.EmptyClass();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0 //将第一个引用变量推送到栈顶
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V // invokespecial调用父类构造方法
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/lzl/bytecode/chapter1/EmptyClass;
}
SourceFile: "EmptyClass.java"
 */
