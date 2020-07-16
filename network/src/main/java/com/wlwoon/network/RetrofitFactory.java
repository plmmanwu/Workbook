package com.wlwoon.network;

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

    public <T> void creat(Class<T> cls) {

    }

}
