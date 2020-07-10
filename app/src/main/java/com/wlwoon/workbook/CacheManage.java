package com.wlwoon.workbook;

/**
 * Created by wxy on 2020/7/10.
 */

public class CacheManage {
    private static final CacheManage ourInstance = new CacheManage();

    public static CacheManage getInstance() {
        return ourInstance;
    }

    private CacheManage() {
    }

    DemoData mDemoData;


    public DemoData getDemoData() {
        return mDemoData;
    }

    public void setDemoData(DemoData demoData) {
        mDemoData = demoData;
    }
}
