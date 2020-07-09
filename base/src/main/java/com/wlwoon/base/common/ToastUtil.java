package com.wlwoon.base.common;

import android.view.Gravity;
import android.widget.Toast;

import com.wlwoon.base.Base;

/**
 * Created by wxy on 2020/7/9.
 */

public class ToastUtil {
    private static final ToastUtil ourInstance = new ToastUtil();
    Toast toast = null;

    public static ToastUtil getInstance() {
        return ourInstance;
    }

    private ToastUtil() {
        toast = Toast.makeText(Base.getInstance().getContext(), "", Toast.LENGTH_SHORT);
    }

    public void showLong(String msg){
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public void showShort(String msg){
        toast.setText(msg);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void cancel() {
        toast.cancel();
    }


}
