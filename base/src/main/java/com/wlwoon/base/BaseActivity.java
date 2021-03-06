package com.wlwoon.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wlwoon.base.common.BitmapUtil;
import com.wlwoon.base.common.StatusBarUtils;
import com.wlwoon.base.common.ToastUtil;
import com.wlwoon.base.common.Utils;
import com.wlwoon.base.interfaces.ActivityForResultCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wxy on 2020/7/8.
 */

public abstract class BaseActivity extends AppCompatActivity {


    private Unbinder mBind;
    protected Context mContext;
    protected Activity mActivity;
    private ActivityForResultCallback callback;
    private int code;//requestCode

    protected String TAG = "wxy";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mActivity = this;
        mContext = this;
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);
        StatusBarUtils.setTransparentForWindow(this);//透明状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        Intent intent = getIntent();
        initData(savedInstanceState, intent == null ? null : intent.getExtras());//初始化数据
        setStatusBarStyle();
        AppManage.getInstance().inStack(this);
    }

    private void setStatusBarStyle() {
        final Handler handler = new Handler();
        View decorView = getWindow().getDecorView();
        decorView.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = StatusBarUtils.statusBarShot(mActivity);
                boolean bright = BitmapUtil.isBitmapBright(bitmap);
                if (bright) {
                    StatusBarUtils.setDarkMode(mActivity);
                } else {
                    StatusBarUtils.setLightMode(mActivity);
                }
                bitmap.recycle();
//                handler.postDelayed(this, 1000);
            }
        });
    }


    protected abstract int getLayoutId();

    protected abstract void initData(Bundle savedInstanceState, Bundle extras);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        AppManage.getInstance().outStack(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ToastUtil.getInstance().cancel();
    }

    //点击EditText以外的区域隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View focusView = getCurrentFocus();
            if (shouldHideKeyboard(focusView, ev)) {
                hideKeyboard(focusView.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean shouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;

    }


    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void startActivity(Context context, Class<?> cls) {
        startActivityWithData(context, cls, null);
    }

    protected void startActivityWithData(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }



    private void showAlertDialog() {


    }

    protected void startActivityForResultWithData(Context context, Class<?> cls, Bundle bundle, int code, ActivityForResultCallback callback) {
        Utils.checkNotNull(callback);
        this.callback = callback;
        this.code = code;
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code);
    }

    protected void startActivityForResult(Context context, Class<?> cls, int code, ActivityForResultCallback callback) {
        startActivityForResultWithData(context, cls, null, code, callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == code) {
            callback.result(data, requestCode);
        }
    }

    protected void finishActivity() {
        finishActivityWithData(null);
    }

    protected void finishActivityWithData(Bundle bundle) {
        Intent intent = null;
        if (bundle != null) {
            intent = new Intent();
            intent.putExtras(bundle);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

}
