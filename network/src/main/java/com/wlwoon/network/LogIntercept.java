package com.wlwoon.network;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wxy on 2020/7/16.
 */

public class LogIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long l = System.nanoTime();
        Logger.i(String.format("send request %s on %s%n%s"),request.url(),chain.connection(),request.headers());
        Response proceed = chain.proceed(request);
        long l1 = System.nanoTime();
        Logger.i(String.format("Received response for s% in %.lfms%n%s"),proceed.request().url(),(l1-l)/1e6d,proceed.headers());

        return proceed;
    }
}
