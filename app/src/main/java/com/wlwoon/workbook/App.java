package com.wlwoon.workbook;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.Tool.Common.CommonApplication;
import com.Tool.Global.Constant;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.wlwoon.base.Base;
import com.wlwoon.glide.GlideImageLocader;
import com.wlwoon.imageloader.ImageLoaderConfig;
import com.wlwoon.imageloader.ImageLoaderManager;
import com.wlwoon.imageloader.LoaderEnum;
import com.wlwoon.workbook.share.DaoMaster;
import com.wlwoon.workbook.share.DaoSession;

/**
 * Created by wxy on 2020/7/8.
 */

public class App extends Application {

    static Context mContext;
    private static DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        CommonApplication.initialise(this);//初始化音频合成库
        Base.getInstance().init(this);
        ImageLoaderConfig loaderConfig = new ImageLoaderConfig
                .Builder(LoaderEnum.GLIDE, new GlideImageLocader())
                .build();

        ImageLoaderManager.getInstance().init(this, loaderConfig);

        Logger.addLogAdapter(new AndroidLogAdapter());

        initGreenDaoDb();
        Stetho.initializeWithDefaults(this);
    }

    static void initGreenDaoDb() {
        //创建指定名称的数据库
        DaoMaster.DevOpenHelper devopenhelper = new DaoMaster.DevOpenHelper(mContext, Constant.DB_NAME, null);
        //获取可写的数据库
        SQLiteDatabase writableDatabase = devopenhelper.getWritableDatabase();
        //获取可写数据库对象
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        //获取操作数据库的管理者
//        DaoMaster.dropAllTables(daoMaster.getDatabase(),true);
//        DaoMaster.createAllTables(daoMaster.getDatabase(),true);
        daoSession = daoMaster.newSession();
    }

    //添加获取操作数据的管理者方法,方便调用
    public static DaoSession getDaoSession() {
        return daoSession;
    }


}
