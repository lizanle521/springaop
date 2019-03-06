### channel功能说明
io.netty.channel.Channel是Netty网络操作抽象类，它聚合了一组功能，包括但不限于：
- 网络的读，写
- 客户端发起链接
- 主动关闭链路
- 获取通信双方的网络地址
- 获取channel的EventLoop
- 获取缓冲分配器ByteBufAllocator 和 pipeline等

#### channel工作原理
Channel是Netty抽象出来的网络I/O读写相关的接口，为什么不使用JDK NIO原生的channel而要另起炉灶呢，主要原因如下：
- JDK的ScoketChannel和ServerSocketChannel没有统一的Channel接口业务供开发者使用，对于用户而言，没有统一的操作视图，使用起来不方便
- JDK的SocketChannel和ServerSocketChannel的主要职责是网络IO操作，由于他们是SPI类接口，由具体的虚拟机厂家来提供，所以通过继承SPI功能类
来扩展其功能的难度很大；直接实现SocketChannel和ServerSocketChannel的实现类，其工作量和重新开发一个Channel的功能的工作量是差不多的。
- Netty的channel需要和Netty的整体架构融合在一起，例如IO模型，基于ChannelPipeLine的定制模型，以及基于元数据描述配置化的TCP参数等，这些JDK的
SocketChannel和ServerSocketChannel都没有提供，需要重新封装
- 自定义Channel,功能实现更加灵活

基于以上原因，netty重新设计了Channel接口，给予了很多不同的实现。它的设计原理很简单，功能比较复杂，主要设计理念如下：
- 在channel接口层，采用Facade模式统一封装，将网络IO操作以及相关的的其他操作封装起来，统一处理
- channel接口定义尽量大而全，为SocketChannel 和 ServerSocketChannel提供统一视图，由不同的子类实现，公共类在父类中实现
- 具体实现采用聚合而非包含的方式，将相关功能聚合在channel中，由channel统一负责调配，功能实现更加灵活

Channel功能比较复杂，我们对他进行分类介绍

##### 网络I/O操作
- Channel read():从当前channel中读取数据到第一个inbound缓冲区中，如果数据被成功读取，触发ChannelHandler.channelRead(ChannelHandlerContext ctx,Object obj)事件。
读取操作API完成以后，紧接着触发ChannelHandler.channelReadComplete(ChannelHandlerContext ctx,Object obj),这样业务的Handler可以决定是否要继续读取数据
- ChannelFuture write(Object msg):请求将当前的msg经过pipeline写入到目标channel中，注意，write操作只是将消息存入到消息发送环形数组中，并没有马上发送，只有调用flush,
才会被写入channel中，发送给对方。
- ChannelFuture write(Object msg,ChannelPromise promise):功能与write(Object msg)相同，但是携带了ChannelPromise负责获取写入操作的结果
- ChannelFuture writeAndFlush(Object msg)
- ChannelFuture writeAndFlush(Object msg,ChannelPromise promise)
- Channel flush():将消息写入channel并发送给对方
- ChannelFuture close(ChannelPromise promise):主动关闭连接,可以通过promise获取关闭结果。该操作会级联触发所有ChannelHandler中的
ChannelHandler.close(ChannelHandlerContext ctx,ChannelPromise promise)事件
- ChannelFuture connect(SocketAddress remoteAddress):客户端使用指定服务端地址发起连接，该操作会级联触发ChannelHandler中的同名方法
- ChannelFuture connect(SocketAddress remoteAddress,SocketAddress localaddress):先绑定本地地址，然后连接远程服务器
- ChannelFuture bind(SocketAddress localaddress):绑定本地地址，会级联触发ChannelHandler同名方法
- ChannelConfig config():获取当前channel配置信息
- boolean isOpen():判断channel是否打开
- boolean isRegistered():判断channel是否注册到eventloop上
- boolean isActive():判断channel是否处于激活状态
- ChannelMetaData metadata():获取channel元数据描述信息，包括tcp参数
- SocketAddress localAddress()
- SocketAddress remoteAddress()

##### 其他常用API功能说明
第一个比较重要的是是eventLoop().Channel需要注册到EventLoop的多路复用器上，用于处理IO时事件。通过eventLoop可以获取到Channel注册的eventLoop。
eventLoop的本质是处理网络读写时间的reactor线程。在Netty中，不仅仅用来处理网络时间，也可以执行定时任务和用户自定义的NIOTask等任务

第二个比较常用的是metadata方法。熟悉TCP协议的读者都知道，当创建SOCKET的时候需要指定TCP参数，例如接受和发送的缓冲区大小，tcp超时时间，是否重用地址等。
在netty中，每个channel对应一个物理连接每个连接都有自己的tcp配置。

第三个方法是parent。对于服务端channel而言，他的父channel为空。对于客户端而言，他的父channel是创建它的ServerSocketChannel

第四个方式是id()，他返回channelId对象channelId是channel的唯一标识，他可能生成的策略如下：
* 机器的Mac地址
* 当前的进程ID
* 当前系统时间毫秒
* 当前系统时间纳秒
* 32位随机数
* 32位自增数

#### Channel源码分析
Channel的实现子类非常多，继承关系复杂，从学习的角度我们抽取最重要的两个NioSocketChannel和NioServerSocketChannel
##### Channel主要的继承关系图
NioServerSocketChannel的继承关系图：
![Alt NioServerSocketChannel](../img/NioServerSocketChannel_class_diagram.png)
NioSocketChannel的继承关系图：
![Alt niosocketchannel](../img/NioSocketChannel_class_diagram.png)

##### AbstractChannel源码分析
###### 成员变量
分析Abstractchannel源码之前，我们分析一下他的成员变量，变量定义源码如下：
```text
private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
     //链路已经关闭异常
    static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = new ClosedChannelException();
    // 物理链路尚未建立异常
    static final NotYetConnectedException NOT_YET_CONNECTED_EXCEPTION = new NotYetConnectedException();

    static {
        // 堆栈设空
        CLOSED_CHANNEL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        NOT_YET_CONNECTED_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }
    // 用于预测下一个报文大小，基于之前的数据采用进行分析
    private MessageSizeEstimator.Handle estimatorHandle;
    // 父channel
    private final Channel parent;
    // 采用默认方式生成全局ID
    private final ChannelId id = DefaultChannelId.newInstance();
    // unsafe实例
    private final Unsafe unsafe;
    // 当前channel的pipeline
    private final DefaultChannelPipeline pipeline;
   
    private final ChannelFuture succeededFuture = new SucceededChannelFuture(this, null);
    private final VoidChannelPromise voidPromise = new VoidChannelPromise(this, true);
    private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
    private final CloseFuture closeFuture = new CloseFuture(this);

    private volatile SocketAddress localAddress;
    private volatile SocketAddress remoteAddress;
    // eventLoop
    private final EventLoop eventLoop;
    private volatile boolean registered;

    /** Cache for the string representation of this channel */
    private boolean strValActive;
    private String strVal;
```
###### 核心API源码分析
首先看下网络读写操作，前面介绍到，它会触发ChannelPipeline对应的事件方法。Netty基于事件驱动，我们可以理解为Channel进行IO操作时会产生对应的IO事件，
然后驱动事件在pipeline中传播 ，由对应的ChannelHandler处理，不关心直接忽略。相比aop,性能更好。
网络IO操作直接调用pipeline中的相关方法，由pipeline中对应的ChannelHandler进行具体的逻辑处理。代码如图：
```text
 @Override
    public ChannelFuture bind(SocketAddress localAddress) {
        return pipeline.bind(localAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress) {
        return pipeline.connect(remoteAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return pipeline.connect(remoteAddress, localAddress);
    }

    @Override
    public ChannelFuture disconnect() {
        return pipeline.disconnect();
    }

    @Override
    public ChannelFuture close() {
        return pipeline.close();
    }
```

##### AbstractNioChannel源码分析
###### 成员变量
```text
    private static final InternalLogger logger =
            InternalLoggerFactory.getInstance(AbstractNioChannel.class);
    // 由于NIO Channel ,NioSocketChannel,NioServerSocketChannel需要共用，所以定义了NioSocketChannel,NioServerSocketChannel的父类
    private final SelectableChannel ch;
    // 代表JDK的selectionKey的OP_READ
    protected final int readInterestOp;
    // 该selectionKey是channel注册到eventLoop返回的选择键，由于channel会面对多个业务线程的并发写操作，所以需要用volatile保证可见性
    private volatile SelectionKey selectionKey;
    private volatile boolean inputShutdown;

    /**
     * The future of the current connection attempt.  If not null, subsequent
     * connection attempts will fail.
     */
    // 连接结果，如果非空的话，后续的连接将会失败
    private ChannelPromise connectPromise;
    // 连接超时定时器
    private ScheduledFuture<?> connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
```

###### 核心API源码分析
首先是Channel的注册。
```text
 protected void doRegister() throws Exception {
        boolean selected = false;
        for (;;) {
            try {
                selectionKey = javaChannel().register(eventLoop().selector, 0, this);
                return;
            } catch (CancelledKeyException e) {
                if (!selected) {
                    // Force the Selector to select now as the "canceled" SelectionKey may still be
                    // cached and not removed because no Select.select(..) operation was called yet.
                    eventLoop().selectNow();
                    selected = true;
                } else {
                    // We forced a select operation on the selector before but the SelectionKey is still cached
                    // for whatever reason. JDK bug ?
                    throw e;
                }
            }
        }
    }
```
注册channel的时候需要制定监听的网络操作位来标示channel对哪几类网络时间感兴趣，具体的定义如下：
 * public static final int OP_READ = 1 << 0; 读操作位
 * public static final int OP_WRITE = 1 << 2; 写操作位
 * public static final int OP_CONNECT = 1 << 3; 客户端连接服务端操作位
 * public static final int OP_ACCEPT = 1 << 4 ;服务端接收客户端操作位
 AbstractNioChannel 注册的是0，说明对任何事情都不感兴趣，仅仅完成注册。注册的时候可以指定附件，后续channel接收到网络事件通知可以从
 SelectionKey中重新获取之前的附件进行处理，此处将AbstractNioChannel的实现子类自身当作附件注册。如果注册channel成功，则返回selectionKey,
 通过selectionKey可以从多路复用器selector中获取channel对象。
 
 如果当前注册返回的selectionKey已经被取消，则抛出CancelledKeyException 异常。




