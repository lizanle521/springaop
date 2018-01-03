package com.lzl.disruptor.demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.aspectj.weaver.ast.Var;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Lizanle on 2018/1/3.
 */
public class Testdisruptor {
    /**
     * 普通写法
     * @throws InterruptedException
     */
    @Test
    public void testDisruptor() throws InterruptedException {
        // 为消费者构建线程
        ThreadFactory threadFactory =  Executors.defaultThreadFactory();
        // 产生事件的工厂
        LongEventFacotory longEventFacotory = new LongEventFacotory();

        // 指定ringbuffer大小，必须为2的整数倍
        int bufferSize = 2^10;

        // 构建disruptor
        Disruptor<LongEvent> longEventDisruptor = new Disruptor<LongEvent>(longEventFacotory,bufferSize,threadFactory);

        // 绑定事件处理器
        longEventDisruptor.handleEventsWith(new LongEventHandler());

        // 启动disruptor,启动所有的线程
        longEventDisruptor.start();

        // 从disruptor中获取ringbuffer
        RingBuffer<LongEvent> ringBuffer = longEventDisruptor.getRingBuffer();

        // 构建生产者
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        //构建数据，发送异步消息
        ByteBuffer allocate = ByteBuffer.allocate(8);
        for (long i = 0L;true; i++) {
            allocate.putLong(0,i);
            producer.onData(allocate);
            Thread.sleep(1000L);
        }
    }

    /**
     * lambada表达式写法
     * @throws InterruptedException
     */
    @Test
    public void testDisruptorUseJava8() throws InterruptedException {
        int bufferSize = 2^10;
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, Executors.defaultThreadFactory());
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("event:"+event));
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long i = 0; true; i++) {
            byteBuffer.putLong(0,i);
            // GC压力比较小的时候用这个，否则用下边的那个。因为这种方法会创建一个额外的对象去保存byteBuffer
            ringBuffer.publishEvent((evetn,seq)->evetn.setValue(byteBuffer.getLong(0)));
            //ringBuffer.publishEvent((evetn,seq,buffer)->evetn.setValue(buffer.getLong(0)),byteBuffer);
            Thread.sleep(1000);
        }
    }

    /**
     * 方法引用
     * @throws InterruptedException
     */
    @Test
    public void testDisruptorUseMethodReference() throws InterruptedException {
        int bufferSize = 2^10;

        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, Executors.defaultThreadFactory());

        disruptor.handleEventsWith(Testdisruptor::handEvent);

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (long i = 0; true; i++) {
            byteBuffer.putLong(0,i);
            ringBuffer.publishEvent(Testdisruptor::translate,byteBuffer);
            Thread.sleep(1000);
        }
    }

    public static void handEvent(LongEvent longEvent,long sequence,boolean endOfBatch){
        System.out.println("event:"+longEvent);
    }

    public static void translate(LongEvent longEvent,long sequence,ByteBuffer byteBuffer){
        longEvent.setValue(byteBuffer.getLong(0));
    }

}
