package com.lzl.netty.chapter2.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lizanle
 * @data 2019/2/17 2:27 PM
 */
public class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private int port ;
    private ServerSocketChannel servChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        this.port = port;
        try {
            selector = Selector.open();// 多路复用器
            servChannel = ServerSocketChannel.open(); // 所有客户端连接的父管道
            servChannel.configureBlocking(false);// 配置连接为非阻塞
            servChannel.socket().bind(new InetSocketAddress(port));//绑定端口
            servChannel.register(selector,SelectionKey.OP_ACCEPT);//监听连接事件

            System.out.println("the time server started in port:"+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        // 无限循环就绪的key
        while (!stop){
            try {
                //System.out.println("before select :"+System.currentTimeMillis());
                selector.select(1000);// 每隔1000ms被唤醒一次
                //System.out.println("after select :"+System.currentTimeMillis());
                Set<SelectionKey> selectionKeys = selector.selectedKeys();//此次已经就绪的key的集合
                SelectionKey key = null;
                Iterator<SelectionKey> it = selectionKeys.iterator();

                while (it.hasNext()){
                    key = it.next();
                    it.remove();//已经拿到了就可以删除迭代元素了

                    try {
                        handlerInput(key);
                    } catch (IOException e) {
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            if(key.isAcceptable()){
                // 处理新接入的请求消息
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();// 这句返回以后正式表明 tcp 三次握手完成
                sc.configureBlocking(false);
                // 将新的连接添加到多路复用器
                sc.register(selector,SelectionKey.OP_READ);
            }

            if(key.isReadable()){
                // 读取连接的数据
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readbytes = sc.read(buffer); // 异步非阻塞的情况下 read方法是非阻塞的，
                // 那么返回结果有3种可能
                // 1. 返回值大于0,读到了字节，对字节进行编解码
                // 2. 返回值等于0，没有读取到字节，正常场景，忽略
                // 3. 返回值为-1，链路已经关闭，需要关闭socketchannel,释放资源

                if(readbytes > 0 ){
                    // 将缓冲区当前的limit设置为position,将position设置为0，一般是写入以后 需要读取的情况下调用flip操作
                    buffer.flip();

                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);

                    String s = new String(bytes, "utf-8");
                    System.out.println("the server received order:"+s);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(s) ? System.currentTimeMillis()+"":"BAD ORDER";

                    doWrite(sc,currentTime);
                }
            }

        }
    }

    private void doWrite(SocketChannel sc,String s) throws IOException{
        if(s != null && s.trim().length() > 0){
            byte[] bytes = s.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);

            buffer.flip();
            sc.write(buffer);
            // 由于socketchannel 是异步非阻塞的，可能会出现写半包问题。
            // 不断轮询selector 将没有发送完成的 bytebuffer 发送完毕。
            // 可以通过buffer.hasRemain方法判断消息是否发送完成
        }
    }
}
