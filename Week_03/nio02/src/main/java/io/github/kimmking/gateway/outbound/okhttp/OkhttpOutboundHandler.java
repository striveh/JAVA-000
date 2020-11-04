package io.github.kimmking.gateway.outbound.okhttp;

import io.github.kimmking.gateway.outbound.NamedThreadFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.*;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class OkhttpOutboundHandler {

    private OkHttpClient okHttpClient;

    private ExecutorService proxyService;

    private String backendUrl;

    public OkhttpOutboundHandler (String backendUrl){
        this.backendUrl = backendUrl.endsWith("/")?backendUrl.substring(0,backendUrl.length()-1):backendUrl;
        int cores =  Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores,cores,keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize),new NamedThreadFactory("OkhttpProxyService"),rejectedExecutionHandler);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1,TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS) // 设置写数据超时
                .readTimeout(30, TimeUnit.SECONDS) // 设置读数据超时
                .build();
    }

    public void handle(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx){
        final String url =  this.backendUrl+fullHttpRequest.uri();
        proxyService.submit(()->fetchGet(fullHttpRequest,ctx,url));
    }

    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        Request.Builder requestBuilder = new Request.Builder();
        inbound.headers().forEach(e->{
            requestBuilder.addHeader(e.getKey(),e.getValue());
        });
        Request request = requestBuilder.url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(inbound,ctx,response);
            }
        });

    }

    private void handleResponse(final FullHttpRequest fullHttpRequest,final ChannelHandlerContext ctx,final Response endpointResponse){
        FullHttpResponse response = null;
        try {
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(endpointResponse.body().bytes()));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", (int) endpointResponse.body().contentLength());
        }catch (Exception e){
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx,e);
        }finally {
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }



    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
