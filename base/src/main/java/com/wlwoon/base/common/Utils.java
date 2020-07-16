package com.wlwoon.base.common;

import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.wlwoon.base.Base;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * Created by wxy on 2020/7/9.
 */

public class Utils {

    public static void checkNotNull(Object o) {
        checkNotNull(o, null);
    }

    public static void checkNotNull(Object o, String errorMsg) {
        if (o == null) {
            throw new NullPointerException(TextUtils.isEmpty(errorMsg) ? o + " not null,please init it first" : errorMsg);
        }
    }

    public static void checkNullReturn(Object o) {
        if (o == null) {
            return;
        }
    }

    /**
     * 可变数组生成 list
     *
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> List<T> genLists(T... ts) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < ts.length; i++) {
            list.add(ts[i]);
        }
        return list;
    }

    public static String[] checkPermission(Permissions... permissions) {
        List<String> strings = new ArrayList<>();
        for (Permissions permission : permissions) {
            String[] array = PermissonsHelp.toPermissionArray(permission);
            for (String s : array) {
                if (ContextCompat.checkSelfPermission(Base.getInstance().getContext(), s) != PackageManager.PERMISSION_GRANTED) {
                    strings.add(s);
                }
            }
        }
        String[] ss = new String[strings.size()];
        String[] toArray = strings.toArray(ss);
        return toArray;
    }


}
