package com.lzl.disruptor.demo;

import com.lmax.disruptor.EventFactory;

/**
 * 便于生产携带数据的事件
 * Created by Lizanle on 2018/1/3.
 */
public class LongEventFacotory  implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
