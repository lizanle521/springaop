package com.lzl.disruptor.demo.clear;

import com.lmax.disruptor.EventHandler;

/**
 * Created by Lizanle on 2018/1/3.
 */
public class ClearEventHandler<T> implements EventHandler<ObjectEvent<T>> {
    @Override
    public void onEvent(ObjectEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        // 如果清楚失败就会导致对象一直会存活到下一次被覆盖
        event.clear();
    }

}
