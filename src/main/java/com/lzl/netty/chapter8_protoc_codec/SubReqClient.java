package com.lzl.netty.chapter8_protoc_codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author lizanle
 * @Date 2019/2/25 13:37
 */
public class SubReqClient {
    public void connect(String host,int port) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
        }finally {
            group.shutdownGracefully();
        }
    }
}
