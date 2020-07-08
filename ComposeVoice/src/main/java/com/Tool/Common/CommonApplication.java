package com.Tool.Common;

import android.content.Context;
import android.os.StrictMode;
import android.view.Gravity;
import android.widget.Toast;

import com.Tool.Function.CommonFunction;
import com.Tool.Function.InitFunction;
import com.Tool.Global.Constant;

import java.lang.ref.WeakReference;

/**
 * Created by 郑童宇 on 2016/05/24.
 */
public class CommonApplication {
    private static boolean initialised;
    private static boolean initialisedInUiThread;

    private Toast messageToast;
    private Toast longMessageToast;

    private static Context ctx;

    private volatile static CommonApplication instance = null;

    public static CommonApplication getInstance() {
        if (instance == null) {
            synchronized (CommonApplication.class) {
                if (instance==null) {
                    instance = new CommonApplication();
                }
            }
        }

        return instance;
    }


    public static synchronized void initialise(Context context) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        ctx = contextWeakReference.get();
//        instance = ctx;

        if (CommonFunction.isEmpty(CommonFunction.GetPackageName())) {
            // 百度定位sdk定位服务或者类似启动remote service的第三方库运行在一个单独的进程
            // 每次定位服务启动的时候，都会调用application::onCreate,创建新的进程
            // 这个特殊处理是，如果从pid找不到对应的processInfo,processName，
            // 则此application::onCreate是被service调用的，直接返回
            return;
        }

        initialised = false;
        initialisedInUiThread = false;
        if (!initialised) {
            initialised = true;

            InitFunction.Initialise(ctx);

            if (Constant.Debug) {
                StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                        new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog();
                StrictMode.VmPolicy.Builder vmPolicyBuilder =
                        new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();
                StrictMode.setThreadPolicy(threadPolicyBuilder.build());
                StrictMode.setVmPolicy(vmPolicyBuilder.build());
            }
        }
    }

    public synchronized void initialiseInUIThread() {
        if (!initialisedInUiThread) {
            initialisedInUiThread = true;

            messageToast = Toast.makeText(ctx, "", Toast.LENGTH_SHORT);
            longMessageToast = Toast.makeText(ctx, "", Toast.LENGTH_LONG);
        }
    }

    public static Context getContext() {
        checkNotNull(ctx);
        return ctx;
    }

    public synchronized boolean isInitialised() {
        return initialised;
    }

    public void showToast(String text, String source) {
        checkNotNull(ctx);
        showToast(text, source, false);
    }

    public void showToast(String text, String source, boolean debug) {
        if (CommonFunction.isEmpty(text)) {
            return;
        }

        if (messageToast == null) {
            messageToast = Toast.makeText(ctx, "", Toast.LENGTH_SHORT);
        }

        messageToast.setText(text);
        messageToast.show();
    }

    public void showToast(String text, String source, boolean debug, boolean isLong) {
        showToast(text, source, debug, isLong, Gravity.BOTTOM);
    }

    public void showToast(String text, String source, boolean debug, boolean isLong, int gravity) {
        if (CommonFunction.isEmpty(text)) {
            return;
        }

        if (isLong) {
            if (longMessageToast == null) {
                longMessageToast = Toast.makeText(ctx, "", Toast.LENGTH_LONG);
            }

            longMessageToast.setText(text);
            longMessageToast.setGravity(gravity, 0, 0);
            longMessageToast.show();
        } else {
            if (messageToast == null) {
                messageToast = Toast.makeText(ctx, "", Toast.LENGTH_SHORT);
            }

            messageToast.setText(text);
            messageToast.setGravity(gravity, 0, 0);
            messageToast.show();
        }
    }

    private static void checkNotNull(Object o) {
        if (o==null) {
            throw new NullPointerException("context not null, please init CommonApplication first");
        }
    }

}