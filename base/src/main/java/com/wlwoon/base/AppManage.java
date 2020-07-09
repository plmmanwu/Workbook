package com.wlwoon.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.wlwoon.base.common.Utils;

import java.util.Stack;

/**
 * Created by wxy on 2020/7/9.
 */

public class AppManage {
    private static final AppManage ourInstance = new AppManage();
    private static Stack<Activity> ACTIVITY_STACK = null;

    public static AppManage getInstance() {
        return ourInstance;
    }

    private AppManage() {
        ACTIVITY_STACK = new Stack<>();
    }

    public void inStack(Activity activity) {
        if (ACTIVITY_STACK==null) {
            ACTIVITY_STACK = new Stack<>();
        }
        ACTIVITY_STACK.add(activity);
    }

    public void outStack(Activity activity) {
        Utils.checkNullReturn(activity);
        ACTIVITY_STACK.remove(activity);
        activity.finish();
    }

    public Activity currentActivity() {
        Activity activity = ACTIVITY_STACK.lastElement();
        return activity;
    }

    public void finishAllActivity() {
        for (Activity activity : ACTIVITY_STACK) {
            if (activity != null) {
                activity.finish();
            }
        }
        ACTIVITY_STACK.clear();
    }

    public void appExit() {

        finishAllActivity();
        ActivityManager activityManager = (ActivityManager) Base.getInstance().getContext().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(Base.getInstance().getContext().getPackageName());
        System.exit(0);

    }
}
