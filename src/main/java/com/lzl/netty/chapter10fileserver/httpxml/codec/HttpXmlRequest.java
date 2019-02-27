package com.lzl.netty.chapter10fileserver.httpxml.codec;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author lizanle
 * @Date 2019/2/27 13:00
 */
public class HttpXmlRequest {

    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpXmlRequest{" +
                "request=" + request +
                ", body=" + body +
                '}';
    }
}
