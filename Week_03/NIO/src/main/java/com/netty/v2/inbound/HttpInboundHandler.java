package com.netty.v2.inbound;

import com.netty.v2.filter.HeaderHttpRequestFilter;
import com.netty.v2.filter.HttpRequestFilter;
import com.netty.v2.outbound.HttpOutboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private String proxyServer;

    private HttpOutboundHandler handler;

    private HttpRequestFilter filter = new HeaderHttpRequestFilter();

    public HttpInboundHandler(String proxyServer){
        this.proxyServer = proxyServer;
        this.handler = new HttpOutboundHandler(this.proxyServer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            LocalDateTime startTime = LocalDateTime.now();
            System.out.println("时间："+startTime+"请求接口开始");
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String uri = fullHttpRequest.uri();
            System.out.println("uri:"+uri);
//            if(uri.contains("/test")){
//                handler(ctx,fullHttpRequest);
//            }else{
//                System.out.println("default processing");
//                //defaulHandler(ctx,fullHttpRequest);
//            }
            handler.handle(fullHttpRequest,ctx,filter);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handler(ChannelHandlerContext ctx, FullHttpRequest fullRequest){
        FullHttpResponse fullHttpResponse = null;
        try{
            String value = "default msg";

            //调用后端服务器
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(proxyServer)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            value = response.body().string();
            System.out.println("value:"+value);


            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", fullHttpResponse.content().readableBytes());
        }catch(Exception e){
            e.printStackTrace();
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        }finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    fullHttpResponse.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(fullHttpResponse);
                }
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
