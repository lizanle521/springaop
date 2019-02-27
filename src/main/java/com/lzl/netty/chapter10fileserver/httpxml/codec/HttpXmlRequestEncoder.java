package com.lzl.netty.chapter10fileserver.httpxml.codec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.net.InetAddress;
import java.util.List;

/**
 * @author lizanle
 * @Date 2019/2/27 12:10
 */
public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest> {


    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {
        // 请求发出的时候，order对象写在http请求的body中。
        ByteBuf byteBuf = encode0(ctx,msg.getBody());
        FullHttpRequest request = msg.getRequest();
        if(request == null){
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                    HttpMethod.GET,"/do",byteBuf);

            HttpHeaders headers = request.headers();
            // new HttpEntity("HOST"); 主机
            headers.set(HttpHeaders.Names.HOST, InetAddress
                    .getLocalHost().getHostAddress());
            // 连接
            headers.set(HttpHeaders.Names.CONNECTION,
                    HttpHeaders.Values.CLOSE);
            // 压缩格式
            headers.set(HttpHeaders.Names.ACCEPT_ENCODING,
                    HttpHeaders.Values.GZIP+","+
                    HttpHeaders.Values.DEFLATE);
            // 字符编码格式 q=0.7表示relative quality factor=0.7,意思是说多大概率可以正确使用该值
            /**
             * 这被称为相对品质因子。它从0到1的范围指定用户喜欢的语言，从HTTP / 1.1规范§14.4中可以看出：
             每个语言范围可以被赋予一个相关的质量值，该质量值表示用户对该范围指定的语言的偏好的估计。质量值默认为“q = 1”。例如，
             Accept-Language: da, en-gb;q=0.8, en;q=0.7
             将意味着：“我更喜欢丹麦语，但会接受英式英语和其他类型的英语。”
             */
            headers.set(HttpHeaders.Names.ACCEPT_CHARSET,
                    "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE,"zh");
            headers.set(HttpHeaders.Names.USER_AGENT,"netty-client-side");
            headers.set(HttpHeaders.Names.ACCEPT,
                    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

        }
        HttpHeaders.setContentLength(request,byteBuf.readableBytes());
        out.add(request);
    }
}
