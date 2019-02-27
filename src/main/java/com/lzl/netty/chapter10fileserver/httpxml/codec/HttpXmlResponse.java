package com.lzl.netty.chapter10fileserver.httpxml.codec;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author lizanle
 * @Date 2019/2/27 14:12
 */
public final class HttpXmlResponse {
    private FullHttpResponse response;
    private Object Result;

    public HttpXmlResponse(FullHttpResponse response, Object result) {
        this.response = response;
        Result = result;
    }

    public FullHttpResponse getResponse() {
        return response;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public Object getResult() {
        return Result;
    }

    public void setResult(Object result) {
        Result = result;
    }

    @Override
    public String toString() {
        return "HttpXmlResponse{" +
                "response=" + response +
                ", Result=" + Result +
                '}';
    }
}
