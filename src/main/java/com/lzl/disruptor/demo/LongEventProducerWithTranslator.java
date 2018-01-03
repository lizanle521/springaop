package com.lzl.disruptor.demo;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 事件发布对应的转换者，只需要关注用哪个转换者就可以发布ByteBuffer了
 * 另外的好处就是里边的转换者可以单独的用一个类，独立的进行测试
 * Created by Lizanle on 2018/1/3.
 */
public class LongEventProducerWithTranslator {
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent,ByteBuffer> TRANSLATOR_ONE_ARG =
            (longEvent, sequence, byteBuffer) -> longEvent.setValue(byteBuffer.getLong(0));

    public void onData(ByteBuffer bb){
        ringBuffer.publishEvent(TRANSLATOR_ONE_ARG,bb);
    }
}
