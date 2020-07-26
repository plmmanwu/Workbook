package com.wlwoon.network;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by wxy on 2020/7/16.
 */

public class LogIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long t1 = System.nanoTime();
        Logger.i(String.format("send request %s on %s%n%s",request.url(),chain.connection(),request.headers()));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
//        Logger.i(String.format("Received response for %s in %s%n%s"),proceed.request().url(),(l1-l)/1e6d,proceed.headers(),proceed.body().string());
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        Logger.i(String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()));


        return response;
    }
}
