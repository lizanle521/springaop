package com.lzl.netty.chapter10fileserver.httpxml.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;

import java.util.List;

/**
 * @author lizanle
 * @Date 2019/2/27 14:30
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {
    public HttpXmlResponseDecoder(Class<?> clazz) {
        super(clazz);
    }

    public HttpXmlResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse response, List<Object> out) throws Exception {
        HttpXmlResponse httpXmlResponse = new HttpXmlResponse(
                response, decode0(ctx, response.content()));
        out.add(httpXmlResponse);
    }
}
