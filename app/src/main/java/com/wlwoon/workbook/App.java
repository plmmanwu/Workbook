package com.wlwoon.workbook;

import android.app.Application;

import com.Tool.Common.CommonApplication;

/**
 * Created by wxy on 2020/7/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonApplication.initialise(this);//初始化音频合成库
    }


}