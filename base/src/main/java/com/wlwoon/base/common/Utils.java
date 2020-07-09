package com.wlwoon.base.common;

import android.text.TextUtils;

/**
 * Created by wxy on 2020/7/9.
 */

public class Utils {

    public static void checkNotNull(Object o) {
        checkNotNull(o,null);
    }

    public static void checkNotNull(Object o, String errorMsg) {
        if (o==null) {
            throw new NullPointerException(TextUtils.isEmpty(errorMsg) ? o + " not null,please init it first" : errorMsg);
        }
    }

    public static void checkNullReturn(Object o) {
        if (o==null) {
            return;
        }
    }

//    public static Bundle getBundle(Object... objects) {
//
//    }
}
