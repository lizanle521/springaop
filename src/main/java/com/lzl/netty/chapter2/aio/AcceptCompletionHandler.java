package com.lzl.netty.chapter2.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author lizanle
 * @data 2019/2/19 3:09 PM
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        //  既然客户端已经接收成功，为什么还要再次调用accept方法呢
        // 调用asynchronousServerSocketChannel的accept方法以后，如果有的新的客户端连接接入
        // 系统将回调我们传入的completionHandler实例的completed方法，表示新的客户端接入成功
        // asynchronousSocketChannel 可以接收成千上万的客户端
        attachment.asynchronousServerSocketChannel.accept(attachment,this);

        // 链路建立成功以后，服务端需要接收客户端的请求消息。预分配1m的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        // read方法参数解释：
        // 1。 byteBuffer,接收缓冲区，从异步channel读取数据包
        // 2。 attachment,异步channel携带的附件，通知毁掉的时候作为入参使用
        // 3. 接收通知回调的业务handler，在本例中为readCompletionHandler;
        result.read(allocate,allocate,new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.countDownLatch.countDown();
    }
}
