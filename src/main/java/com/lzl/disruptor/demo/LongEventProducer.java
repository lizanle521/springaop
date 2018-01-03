package com.lzl.disruptor.demo;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 产生事件的生产者
 * Created by Lizanle on 2018/1/3.
 */
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb)
    {
        long next = ringBuffer.next();
        try {
            LongEvent longEvent = ringBuffer.get(next);
            longEvent.setValue(bb.getLong(0));
        } finally {
            ringBuffer.publish(next);
        }
    }
}
