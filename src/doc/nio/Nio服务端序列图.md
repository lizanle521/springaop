# Nio服务端序列图
```sequence
participant NioServer
participant ReactorThread
participant IoHandler

NioServer->NioServer:1. 打开ServerSocketChannel
NioServer->NioServer:2. 绑定监听地址InetSocketAddress
NioServer->ReactorThread:3.open()方法创建Selector,启动线程
NioServer->NioServer:4. 将ServerSocketChannel注册到Selector,监听
ReactorThread->ReactorThread:5. Selector轮询就绪的key
```