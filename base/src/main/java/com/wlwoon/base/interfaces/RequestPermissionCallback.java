package com.wlwoon.base.interfaces;


/**
 * Created by wxy on 2020/7/13.
 */

public interface RequestPermissionCallback {
    void passed(int requestCode);

    void denied(int requestCode);
}
