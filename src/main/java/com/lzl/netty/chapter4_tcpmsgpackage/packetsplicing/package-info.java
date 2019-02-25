package com.lzl.netty.chapter4_tcpmsgpackage.packetsplicing;
/**
 * tcp粘包
 * 由于发生了粘包问题，服务端没有拆包机制，导致收到的结果并不正确
 * 服务端收到结果如下：
 *
 * QUERY TIME ORDER
 QUERY TIME ORDER
 。。。。。省略
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORD,the counter is 0
 The time server received order:
 。。。。。。省略
 * QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER
 QUERY TIME ORDER,the counter is 1

 客户端收到的回应是
 Now is :BAD ORDER
 BAD ORDER
 ,the counter is0

 显然，服务端和客户端都发生了粘包

 netty 提供的LineBasedFrameDecoder 来处理这个问题

 */