package com.lzl.disruptor.demo;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventProcessorFactory;
import com.lmax.disruptor.dsl.ProducerType;
import com.lzl.disruptor.demo.clear.ClearEventHandler;
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

    /**
     * 方法引用
     * 优化disruptor的方法
     * @throws InterruptedException
     */
    @Test
    public void testDisruptorUseMethodReference2() throws InterruptedException {
        LongEventFacotory facotory = new LongEventFacotory();
        int bufferSize = 2^10;
        // 构建的时候指明是单线程产生数据，能够获得最大性能
        // BlockingWaitStrategy 是默认的等待策略，利用一个典型的lock和condition去进行线程的唤醒和等待
        // 它是最慢的等待策略，但是他能最大限度的保护cpu的使用，在许多不同的部署情况下有着更加一致的行为，对部署的系统的有一定的了解能够允许进行策略的替换以换取额外的性能
        // SleepingWaitStrategy 睡眠等待 利用 LockSupport.parkNanos(1),让一般的linux系统等待60us
        // YieldingWaitStrategy 谦让等待 利用Thread.yeild()进行让出cpu， 是两个低延迟场景下的等待策略之一
        // BusySpinWaitStrategy 忙等。 切记不要使用。非常占cpu，除非handler线程小于物理核心数
        Disruptor<LongEvent> disruptor = new Disruptor(facotory, bufferSize,Executors.defaultThreadFactory(),
                ProducerType.SINGLE,new BlockingWaitStrategy());
        // 性能测试结果
        /**
         * Multiple Producer
         * Run 0, Disruptor=26,553,372 ops/sec
         Run 1, Disruptor=28,727,377 ops/sec
         Run 2, Disruptor=29,806,259 ops/sec
         Run 3, Disruptor=29,717,682 ops/sec
         Run 4, Disruptor=28,818,443 ops/sec
         Run 5, Disruptor=29,103,608 ops/sec
         Run 6, Disruptor=29,239,766 ops/sec

         Single Producer

         Run 0, Disruptor=89,365,504 ops/sec
         Run 1, Disruptor=77,579,519 ops/sec
         Run 2, Disruptor=78,678,206 ops/sec
         Run 3, Disruptor=80,840,743 ops/sec
         Run 4, Disruptor=81,037,277 ops/sec
         Run 5, Disruptor=81,168,831 ops/sec
         Run 6, Disruptor=81,699,346 ops/sec
         */
        // 对象清理
        disruptor.handleEventsWith(Testdisruptor::handEvent).then(Testdisruptor::clearEvent);

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

    public static void clearEvent(LongEvent longEvent,long sequence,boolean endOfBatch){
        longEvent.clear();
    }

    public static void translate(LongEvent longEvent,long sequence,ByteBuffer byteBuffer){
        longEvent.setValue(byteBuffer.getLong(0));
    }

    @Test
    public void testClearObjectFromRingBuffer(){

    }

}
