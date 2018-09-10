package com.lzl.springaop;

import org.junit.Test;

import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2018/9/10.
 */
public class TestRadomAccessFile {

    public void randomRead(String path,int pointer) throws Exception{
        /**
         * r 代表以只读方式打开指定文件
         * rw 以读写方式打开指定文件
         * rws 读写方式打开，并对内容或元数据都同步写入底层存储设备
         * rwd 读写方式打开，对文件内容的更新同步更新至底层存储设备
         *
         * **/
        RandomAccessFile r = new RandomAccessFile(path, "r");
        System.out.println("randomaccessfile文件初始指针是:"+r.getFilePointer());
        //移动文件指针
        r.seek(pointer);
        byte[] buffer = new byte[1024];
        int hasread = 0;//保存已经读取的字节
        while ((hasread = r.read(buffer)) > 0){
            System.out.println(new String(buffer,0,hasread));
        }


    }
     @Test
    public void testRead() throws Exception{
        randomWrite("d:\\c.txt");
         randomRead("d:\\c.txt",0);
     }

     public void randomWrite(String path) throws Exception {
         RandomAccessFile raf=new RandomAccessFile(path, "rw");
         raf.seek(raf.length());
         raf.write("我是追加的\n".getBytes());
     }
}
