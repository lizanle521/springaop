## ByteBuf功能说明
当我们进行数据传输的时候，往往需要缓冲区，常用的缓冲区就是JDK NIO类库提供的java.nio.Buffer.
实际上，7种基础类型（Boolean类除外）都有自己的缓冲区实现，对于NIO编程而言，我们主要使用的ByteBuffer. 从功能角度而言，ByteBuffer完全满足
NIO编程的需要。但是由于NIO编程的复杂性，ByteBuffer有其局限性。主要缺点如下：
 * ByteBuffer长度固定，一旦分配完成，它的容量不能动态扩展和收缩。当需要编码的POJO对象大于ByteBuffer的容量时，发发生索引越界
 * ByteBuffer只有一个标示位置的指针position,读写的时候需要手动调用flip（）和 rewind等，使用者必须小心处理这些api,否则容易导致程序失败
 * ByteBuffer的API功能有限，一些高级和实用的特性他不支持，需要使用者自己实现
为了弥补这些缺陷，Netty提供了自己的ByteBuf

### ByteBuf的工作原理
不同ByteBuf实现类的工作原理不尽相同，本小节我们从ByteBuf的设计原理出发，一起探寻netty byteBuf的设计理念。
首先，ByteBuf依然是一个Byte数组的缓冲区，他的基本功能应该与JDK的ByteBuffer一致，提供以下几类基本功能：
 * 7种java基础类，byte数组，ByteBuffer等的读写
 * 缓冲区自身的copy和slice等
 * 设置网络字节序
 * 构造缓冲实例
 * 操作位置指针等方法
 
 #### ByteBuffer
 由于JDK的Bytebuffer已经提供了这些基础能力的实现，因此，NettyByteBuffer的实现有两种策略：
 * 参考JDK的ByteBuffer的实现，增加额外的功能，解决原ByteBuffer的缺点。
 * 聚合JDKByteBuffer，通过Facade模式对其进行包装，可以减少自身的代码量，降低实现成本
 
 JDK ByteBuffer由于只有一个位置用于处理读写操作，因此每次读写的时候都需要调用额外的flip 和 clear等方法，否则功能将出错，他的典型用法如下：
 ```text
ByteBuffer buffer = ByteBuffer.allocate(88);
String value = "Netty权威指南";
buffer.put(value.getBytes());
buffer.flip();

byte[] arr = new byte[buffer.remaining());
buffer.get(arr);
String s = new String(arr);

```
我们看一下flip()操作前后的对比：
操作前：
![Alt flipbefore](../img/bytebuffer_beforeflip.png)
操作后：
![Alt flipafter](../img/bytebuffer_afterflip.png)
 flip操作是将position置为0，将limit置为position,capacity不变。如果不做flip操作，读取到的将是position 到 capacity之间的错误内容
 
 #### ByteBuf
 与之不同的是，Netty的ByteBuf通过两个位置指针来协助缓冲区的读写操作，读操作使用readerIndex，写操作使用writeIndex。readerIndex和
 writeIndex取值一开始都是0，在读取之后，0-readerIndex 就会被视为discard，调用discardReadBytes方法，可以释放这部分空间，他的作用类似
 ByteBuffer的compact方法。readerIndex和writeIndex之间的数据是可读取的，等价于ByteBuffer的position和limit之间的数据，WriteIndex和capacity
 之间是可写的，等价于ByteBuffer的position和 capacity之间的可用空间。
 
 初始化的ByteBuf如下图：
![Alt bytebufinit](../img/bytebuf_init.png)
写入N哥字节以后的bytebuf如图所示
![Alt bytebufwriteN](../img/bytebuf_writeN.png)
读取M（M<N)个字节以后的bytebuf如图所示
![Alt bytebufreadM](../img/bytebuf_readM.png)
调用discardReadBytes以后的byteBuf如图所示：
![Alt bytebufdiscard](../img/bytebuf_afterdiscard.png)
调用clear之后的bytebuf如图所示
![Alt bytebufinit](../img/bytebuf_init.png)

#### byteBuffer动态扩张
我们来分析一下byteBuffer是如何实现动态扩张的，通常情况下，我们对ByteBuffer进行put操作的时候，如果缓冲区可写空间不够，就会发生BufferOverFlowException。
为了避免发生这个问题，通常在put操作的时候就会对剩余的可用空间进行校验，如果剩余空间不足，需要重新创建一个新的bytebuffer，并且将之前的bytebuffer复制到新的
bytebuffer，最后释放老的bytebuffer。代码示例如下：
```text
if(this.buffer.remaining() < needSize){
    int toBeExtSize = needSize > 128 ? needSize : 128;
    ByteBuffer tmpBuffer = ByteBuffer.allocate(this.buffer.capacity()+toBeExtSize);
    this.buffer().flip();
    tmpBuffer.put(this.buffer);
    this.buffer = tmpBuffer;
}
```
从代码示例中看出，为了防止ByteBuffer溢出，每进行一次put操作，都需要对可用空间进行校验，导致了代码冗余。稍有不慎，可能引入其他问题。
netty为了解决bytebuffer动态扩张的问题，将write操作进行了封装，由bytebuffer的write操作负责进行可用空间的校验，如果缓冲区不足，bytebuf会动态扩张，
对于使用者而言，不需要关心底层的校验和扩展细节，只要不超过设置的最大缓冲区容量即可。

由于NIO的channel读写的参数都是ByteBuffer，因此，Netty的ByteBuf接口必须提供API,以方便的将ByteBuf转换成ByteBuffer，或者将ByteBuffer包装成ByteBuf。
考虑到性能，应该尽量避免缓冲区的复制，内部实现的时候可以考虑聚合一个ByteBuffer的私有指针来代表ByteBuffer。

#### readerIndex 和 writeIndex
Netty的byteBuf提供了两个变量用于支持顺序读取 和 写入操作：readerIndex用于表示读取索引，writerIndex用于标示读取索引。调用byteBuf的read操作的时候，
从readerIndex处开始读取， 
 * readerIndex到writerIndex之间的为可读缓冲区。
 * 从writerIndex到capacity之间为可写缓冲区。
 * 从0到readerIndex为discardable缓冲区，调用discardReadBytes操作来释放这部分空间，可以解决内存，防止byteBuf动态扩张
 
#### discardableBytes
相比于其他java对象，缓冲区的分配和释放是一个耗时的操作，因此，我们需要尽量重用他们，由于缓冲区的动态扩展需要进行字节数的复制，是一个耗时的操作，因此，为了
最大限度的提升程序性能，往往需要尽最大的努力提升缓冲区的重用率。
需要指出的是，调用discardableBytes会发生字节数组的内存复制，频繁调用可能会导致性能下降，因此调用之前你需要确认需要这么做，例如牺牲性能来换更多的可用内存。

#### readableBytes 和 writetablebytes 
可读空间是数据实际存储的区域，以read或者skip开头的任何操作都将会从readerindex开始读取或者跳过制定的字节数，并且readerindex相应的增加
可读空间是尚未被使用的可以填充的空闲空间，任何以write开头的操作都会从writeindex开始向空闲空间写入字节，操作完成以后writeindex相应的增加

#### clear操作
bytebuffer 和  bytebuf都一样，都是将 position，limit ，readerindex,writeindex置0，其他位置填充0x00

#### mark 和 reset
mark 保存当前索引位置，reset将之前保存的索引位置恢复出来

#### 查找操作
- indexOf(int fromIndex,int toIndex,byte value)：从当前bytebuf中定位处首次出现在value的位置，起始索引为fromindex,终点是toIndex
- bytesBefore(byte value):从当前bytebuf中定位出首次出现value的位置，起始索引为readerIndex，终点索引是writerIndex
- bytesBefore(int length,byte value):从当前ByteBuf中定位出首次出现的value的位置，起始位置为readerIndex，终点是readerIndex+length,
如果length大于可读字节数则会抛出异常
- bytesBefore(int index,int length,byte value):从当前bytebuf中定位出首先出现value的位置，起始位置为idnex,终点是index+length
- forEachByte(ByteBufProcessor processor):遍历当前ByteBuf的可读字节数，于ByteBufProcessor设置的查找条件对比，满足条件返回索引，否则返回-1
- forEachByte(int index,int length,ByteBufProcessor processor):对当前bytebuf的可读字节数，起始索引为index，终点为index+length进行遍历
- forEachByteDesc(ByteBuf processor):逆序，起始索引从writerIndex开始，查到readerIndex
- forEachByteDesc(int index,int length,ByteBufProcessor processor):逆序，起始索引从index+length-开始，直到index

#### 生成ByteBuf
类似于数据库的视图（共享数据源），byteBuf提供了多个接口用于创建某个ByteBuf的视图或者复制ByteBuf，具体方法如下：
- duplicate: 返回当前ByteBuf的复制对象，复制后返回的ByteBuf与操作的ByteBuf共享缓冲内容，但是维护自己独立的读写索引，当修改复制后的ByteBuf内容后，
之前的ByteBuf内容也随之改变，双方持有的是同一个内容的指针引用。
- copy:复制一个新的ByteBuf对象，他的内容和索引都是独立的，复制操作本身并不修改源ByteBuf的读写索引
- copy(int index,int length):从制定的索引开始复制，复制的字节长度为length;复制后的bytebuf完全独立。
- slice:返回当前的byteBuf的可读子缓冲区，起始位置从readerIndex到writeIndex,返回后的byteBuf与原byteBuf共享内容，但是读写索引独立维护，该操作并不修改原
bytebuf的readerindex和writeindex
- slice(int index,int length):返回当钱bytebuf的可读子缓冲区，起始位置为index到index+length，内容共享但索引独立

#### 转换成标注的ByteBuffer
ByteBuf-》ByteBuffer方法：
- ByteBuffer nioBuffer():返回可读的缓冲区，共享同一个缓冲区内容。无法感知原来的ByteBuf的扩容操作
- ByteBuffer nioBuffer(int index,int length):从index开始长度为length的ByteBuffer。共享缓冲区，索引独立，无法感知原ByteBuf扩容操作

#### 随机读写
无论是随机读get还是随机写set,byteBuf都会对索引和长度等进行合法性校验，set不能支持动态扩展缓冲区，使用者必须保证当前可写字节数大于写入字节数长度

### ByteBuf源码分析
我们先来看ByteBuf类的继承关系
![Alt bytebufclassdiagram](../img/bytebuf_class_diagram.png)

从内存分配的角度看，byteBuf可以分为两类：
- 对内存（HeapByteBuf)字节缓冲区：特点是内存的分配和回收速度快，可以被JVM自动回收：缺点是如果进行Socket的I/O读写，需要额外做一次内存复制，
将内存对应的缓冲区到内核channel中，性能会有一定的损耗
- 直接内存（DirectByteBuf）字节缓冲区：直接内存，分配和回收速度会慢一些，将它写入socket channel中时，由于少了一次内存复制，速度比对内存快。

正因为有利有弊，所以Netty提供了多种bytebuf供开发者使用，经验表明，ByteBuf的最佳实践是：
- i/o通信线程的读写缓冲区使用DirectByteBuf
- 后端业务消息的编解码模块使用HeapByteBuf. 

 
 