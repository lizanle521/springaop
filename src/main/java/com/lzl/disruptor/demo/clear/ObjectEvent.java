package com.lzl.disruptor.demo.clear;

/**
 * Created by Lizanle on 2018/1/3.
 */
public class ObjectEvent<T> {
    T val;

    void clear(){
        val = null;
    }
}
