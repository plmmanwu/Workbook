package com.wlwoon.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wxy on 2020/7/16.
 */

public class RetrofitFactory {

    private static volatile RetrofitFactory mFactory = null;

    public static RetrofitFactory getInstance() {
        if (mFactory ==null) {
            synchronized (RetrofitFactory.class) {
                if (mFactory==null) {
                    mFactory = new RetrofitFactory();
                }
            }
        }

        return mFactory;
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(new LogIntercept());
        return builder.build();
    }

    public <T> T creat(Class<T> cls,String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(cls);
    }

}
