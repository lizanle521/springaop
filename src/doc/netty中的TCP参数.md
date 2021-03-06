## SO_BACKLOG
backlog指定了内核为此套接口排队的最大连接个数。对于给定的监听套接口，内核要维护两个
队列：未连接队列和已连接队列；
 * 收到客户端syn分节(connect)时在未连接队列创建一个新的节点，然后用服务器的ack+syn响应客户端。
   在客户端未响应服务器的syn之前一直保留在未完成连接队列中。
 * 在三次握手完成后，节点从未完成队列搬到已完成连接队列尾部。
 * 当进程调用accept时，就从已完成连接的队列取一个节点给进程，已完成队列为空时进程睡眠。
 * backlog被规定为两个队列总和的大小，大多数实现默认值为5，netty默认的backlog为100。
 
## SO_TIMEOUT
控制读取操作将阻塞多少毫秒，如果返回值为0，计时器被禁止了，该线程将无限期阻塞。

## SO_SNDBUF
套接字使用的发送缓冲区大小

## SO_RCVBUF
套接字使用的接收缓冲区大小

## SO_REUSEADDR
用于决定如果网络上如果仍然有数据向旧的seversocket传输数据，是否允许新的serversocket绑定到旧的serversocket同样的端口上
so_reuseaddr 的默认值与操作系统有关，在某些操作系统中，是允许重用端口，有些则不允许。

## CONNECT_TIMEOUT_MILLIS
客户端连接超时时间，由于NIO原生的客户端并不提供设置连接超时的接口，因此，Netty采用的是自定义的连接超时定时器负责检测和超时控制

## TCP_NODELAY
激活或者禁止TCP_NODELAY套接字选项，如果是时延敏感型应用，则建议关闭。