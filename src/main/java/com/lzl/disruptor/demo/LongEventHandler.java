package com.lzl.disruptor.demo;


import com.lmax.disruptor.EventHandler;

/**
 * 消费者处理这个事件
 * Created by Lizanle on 2018/1/3.
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("event:"+longEvent);
    }
}
