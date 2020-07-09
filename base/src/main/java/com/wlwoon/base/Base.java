package com.wlwoon.base;

import android.content.Context;

import com.wlwoon.base.common.Utils;

/**
 * Created by wxy on 2020/7/9.
 */

public class Base {
    private volatile static Base mBase = null;
    private Context mContext = null;
    public void init(Context context) {
        mContext = context;
    }

    public static Base getInstance() {
        if (mBase==null) {
            synchronized (Base.class) {
                if (mBase==null) {
                    mBase = new Base();
                }
            }
        }

        return mBase;
    }

    public Context getContext() {
        Utils.checkNotNull(mContext,"context not null,init BaseLib first!");
        return mContext;
    }



}
