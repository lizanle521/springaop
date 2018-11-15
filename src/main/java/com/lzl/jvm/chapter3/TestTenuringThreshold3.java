package com.lzl.jvm.chapter3;

/**
 * -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+UseSerialGC -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGCDateStamps
 * -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution -XX:+PrintHeapAtGC -Xloggc:gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=512
 *
 * Desired survivor size gc日志出现这个，并且打印出  new threshold 1 (max 15)，说明 survivor 区空间不足，导致对象直接提升到老年代，那么就会出现过早提升
 * 过早提升导致的问题就是 很多不到年龄的对象提升到老年代，导致老年代空间不足。会出现 promotion faild ,导致出现 full gc,full gc延时更长，吞吐量下降，对用户造成影响
 */
public class TestTenuringThreshold3 {
    private static final int _1mb = 1*1024*1024;
    public static void main(String[] args) throws InterruptedException {
        byte[] a1,a2,a3,a4;
        a1 = new byte[_1mb/4];
        a2 = new byte[_1mb/4]; // a1 + a2 大于 survivor空间大小的一半
        a3 = new byte[4*_1mb];
        a4 = new byte[4*_1mb];
        a4 = null;
        a4 = new byte[4*_1mb];
        //Thread.currentThread().join(60*60*60*20L);
    }

    /**
     * 1. 初始情况 新生代占用 2695k
     * Heap
     def new generation   total 9216K, used 2695K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     eden space 8192K,  32% used [0x00000000fec00000, 0x00000000feea1c30, 0x00000000ff400000)
     from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     tenured generation   total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     the space 10240K,   0% used [0x00000000ff600000, 0x00000000ff600000, 0x00000000ff600200, 0x0000000100000000)
     Metaspace       used 3273K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 357K, capacity 388K, committed 512K, reserved 1048576K
     */

    /**
     * 2. 分配完 a1 a2以后，新生代占用  2695+ 512k= 3207k
     *  def new generation   total 9216K, used 3203K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     eden space 8192K,  39% used [0x00000000fec00000, 0x00000000fef20f78, 0x00000000ff400000)
     from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     tenured generation   total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     the space 10240K,   0% used [0x00000000ff600000, 0x00000000ff600000, 0x00000000ff600200, 0x0000000100000000)
     Metaspace       used 3274K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 357K, capacity 388K, committed 512K, reserved 1048576K
     */

    /**
     * 3. 再次分配 4096k,新生代几乎沾满 3207+ 4096k = 7303k
     *
     * Heap
     def new generation   total 9216K, used 7300K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     eden space 8192K,  89% used [0x00000000fec00000, 0x00000000ff321100, 0x00000000ff400000)
     from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     tenured generation   total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     the space 10240K,   0% used [0x00000000ff600000, 0x00000000ff600000, 0x00000000ff600200, 0x0000000100000000)
     Metaspace       used 3264K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 355K, capacity 388K, committed 512K, reserved 1048576K
     */

    /**
     * 4. 再次分配 4096k,新生代eden 区无法分配，进行一次minor gc，gc 将  al al 从 eden区拷贝到 from 区，原来的a3的4096k拷贝到from区 放不下，
     * 出现过早提升，然后 a4的4096k可以放到eden区,但是为什么会出现 新生代用了 5174k   ?? 5174-4096-512=566k 是新生代其余的东西，这个内容是初始占用
     * 新生代经过minor gc留下来的内存。老年代按理只有 4096k ,但是却用了 4355k,4355-4096=256k 约为 1/4m空间，这里多余的 1/4m空间怎么提升上来的
     * [GC (Allocation Failure) [DefNew
     Desired survivor size 524288 bytes, new threshold 1 (max 15)
     - age   1:    1048576 bytes,    1048576 total
     : 7134K->1024K(9216K), 0.0033507 secs] 7134K->5379K(19456K), 0.0033921 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     新生代减少 7134-1024=5930k 堆区减少 7134-5379k = 1755k,
     那么从新生代提升到老年代的大小为 5930-1755 = 4175k ,这老年代对象就是  a3 和 80k
     Heap
     def new generation   total 9216K, used 5174K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     eden space 8192K,  50% used [0x00000000fec00000, 0x00000000ff00dbf8, 0x00000000ff400000)
     from space 1024K, 100% used [0x00000000ff500000, 0x00000000ff600000, 0x00000000ff600000)
     to   space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     tenured generation   total 10240K, used 4355K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     the space 10240K,  42% used [0x00000000ff600000, 0x00000000ffa40c08, 0x00000000ffa40e00, 0x0000000100000000)
     Metaspace       used 3274K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 357K, capacity 388K, committed 512K, reserved 1048576K
     */

    /**
     * 5 . 将 eden 区的 4m 引用置空以后，再次申请4m内存，eden区空间不够，进行minor gc,
     * minor gc发现 surivor区有两个 相同年龄的存活对象 大于 等于 survivor区大小的一半，
     * Desired survivor size 524288 bytes, new threshold 1 (max 15)
     - age   1:    1048568 bytes,    1048568 total
     : 7135K->1023K(9216K), 0.0037933 secs] 7135K->5355K(19456K), 0.0038309 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     [GC (Allocation Failure) [DefNew
     Desired survivor size 524288 bytes, new threshold 15 (max 15)
     - age   1:       4560 bytes,       4560 total
     : 5203K->4K(9216K), 0.0011017 secs] 9534K->5358K(19456K), 0.0011184 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     新生代 减少大小 5203 - 4 = 5200k,堆区大小减少 9534-5358= 4176k
     新生代提升 到老年代 5200-4176=1024k，这个1024k大小恰好是 第3步中  from space的大小，明明Threashold=15，却在第二次minor gc的时候 将survivor区全部提升到了老年代
     Heap
     def new generation   total 9216K, used 4238K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     eden space 8192K,  51% used [0x00000000fec00000, 0x00000000ff022880, 0x00000000ff400000)
     from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff4011d0, 0x00000000ff500000)
     to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     tenured generation   total 10240K, used 5353K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     the space 10240K,  52% used [0x00000000ff600000, 0x00000000ffb3a710, 0x00000000ffb3a800, 0x0000000100000000)
     Metaspace       used 3276K, capacity 4496K, committed 4864K, reserved 1056768K
     class space    used 358K, capacity 388K, committed 512K, reserved 1048576K
     */
}
