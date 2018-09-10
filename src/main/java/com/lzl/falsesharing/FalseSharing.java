package com.lzl.falsesharing;

/**
 * 伪共享 导致的性能问题 示例
 *
 * Created by Lizanle on 2018/1/9.
 */
public class FalseSharing  implements Runnable{
    // 根据cpu核心数 N ，从 1 逐个增加到 N 进行测试
    private static  final int NUM_THREADS = 3;
    public static final Long ITERATIONS = 500L * 1000L * 1000L;
    private static ViolationLong[] longs = new ViolationLong[NUM_THREADS];
    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new ViolationLong();
        }
    }
    private final int arrayIndex;
    public FalseSharing(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }


    private final static class ViolationLong {
        public volatile long value  = 0L;
        // 一个对象的引用 4字节，14个对象的引用 56字节。加上 long的8字节，为64字节，恰好填充一个缓存行
        Object p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, pa, pb, pc, pd; // 注释 或者 不注释 决定了是否填充缓存行
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println(Runtime.getRuntime().availableProcessors());
        runTest();
        System.out.println("duration " + (System.currentTimeMillis() - start));
        // 线程数 加了缓存行填充  没加缓存行填充
        // 1     2802           2802
        // 2     2898           13905
        // 3     3146           27663
        // 4     4113           28458
    }

    public static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i){
            longs[arrayIndex].value = i;
        }
    }
}
