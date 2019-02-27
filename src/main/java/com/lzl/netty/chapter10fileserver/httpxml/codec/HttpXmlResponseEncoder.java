package com.lzl.netty.chapter10fileserver.httpxml.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * @author lizanle
 * @Date 2019/2/27 14:18
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse>{

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse response, List<Object> out) throws Exception {
        ByteBuf byteBuf = encode0(ctx, response.getResult());
        FullHttpResponse httpResponse = response.getResponse();
        if(httpResponse != null){
            httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,byteBuf);
        }else{
            httpResponse = new DefaultFullHttpResponse(httpResponse.getProtocolVersion(),
                    httpResponse.getStatus(),byteBuf);
        }
        httpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/xml");
        HttpHeaders.setContentLength(httpResponse,byteBuf.readableBytes());
        out.add(httpResponse);
    }
}
