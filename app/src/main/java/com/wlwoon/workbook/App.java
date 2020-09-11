package com.wlwoon.workbook;

import android.app.Application;

import com.Tool.Common.CommonApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.wlwoon.base.Base;
import com.wlwoon.glide.GlideImageLocader;
import com.wlwoon.imageloader.ImageLoaderConfig;
import com.wlwoon.imageloader.ImageLoaderManager;
import com.wlwoon.imageloader.LoaderEnum;

/**
 * Created by wxy on 2020/7/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonApplication.initialise(this);//初始化音频合成库
        Base.getInstance().init(this);
        ImageLoaderConfig loaderConfig = new ImageLoaderConfig
                .Builder(LoaderEnum.GLIDE, new GlideImageLocader())
                .build();

        ImageLoaderManager.getInstance().init(this, loaderConfig);

        Logger.addLogAdapter(new AndroidLogAdapter());
    }


}
