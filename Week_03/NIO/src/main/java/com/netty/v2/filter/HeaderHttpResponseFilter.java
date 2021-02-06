package com.netty.v2.filter;

import io.netty.handler.codec.http.FullHttpResponse;

import java.time.LocalDateTime;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        System.out.println("HeaderHttpResponseFilter");
    }
}
