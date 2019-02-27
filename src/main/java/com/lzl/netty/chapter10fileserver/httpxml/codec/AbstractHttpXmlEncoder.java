package com.lzl.netty.chapter10fileserver.httpxml.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;


import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * @author lizanle
 * @Date 2019/2/27 12:59
 */
public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {
    IBindingFactory factory = null;
    StringWriter sw = null;
    final static String CHARSET_NAME = "UTF-8";
    final static Charset utf8 = Charset.forName(CHARSET_NAME);

    protected ByteBuf encode0(ChannelHandlerContext ctx,Object body) throws Exception {
        // 通过类获取绑定工厂
        factory = BindingDirectory.getFactory(body.getClass());
        sw = new StringWriter();
        IMarshallingContext msctx = factory.createMarshallingContext();

        // 设置缩进长度
        msctx.setIndent(4);
        // 数据编排，将java对象编排成规范的xml文件
        msctx.marshalDocument(body,CHARSET_NAME,null,sw);
        String s = sw.toString();
        sw.close();
        sw = null;

        ByteBuf byteBuf = Unpooled.copiedBuffer(s, utf8);
        return byteBuf;
    }

    /**
     * 子类可以覆盖这个类去实现自己的行为
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Skip
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(sw != null){
            sw.close();
            sw = null;
        }
    }
}
