package com.lzl.netty.chapter10fileserver.httpxml.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;


import java.io.StringReader;
import java.nio.charset.Charset;

/**
 * @author lizanle
 * @Date 2019/2/27 13:37
 */
public abstract class AbstractHttpXmlDecoder<T>  extends MessageToMessageDecoder<T> {

    private IBindingFactory factory;
    private StringReader sr;
    private Class<?> clazz;
    private boolean isPrint;
    private final static String CHARSET_NAME = "utf-8";
    private final static Charset UTF8 = Charset.forName(CHARSET_NAME);

    public AbstractHttpXmlDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    public AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        factory = BindingDirectory.getFactory(clazz);
        String content = byteBuf.toString(UTF8);
        IUnmarshallingContext umctx = factory.createUnmarshallingContext();
        if(isPrint){
            System.out.println("the body is :" + content);
        }
        sr = new StringReader(content);
        Object o = umctx.unmarshalDocument(sr);
        sr.close();
        return o;
    }

    @Skip
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(sr != null){
            sr.close();
            sr = null;
        }
    }
}
