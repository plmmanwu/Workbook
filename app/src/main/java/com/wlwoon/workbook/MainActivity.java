package com.wlwoon.workbook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wlwoon.base.BaseActivity;
import com.wlwoon.base.common.PermissionCode;
import com.wlwoon.base.common.Permissions;
import com.wlwoon.base.common.ToastUtil;
import com.wlwoon.base.common.Utils;
import com.wlwoon.base.interfaces.ActivityForResultCallback;
import com.wlwoon.contactspicker.Contact;
import com.wlwoon.contactspicker.ContactsPickActivity;
import com.wlwoon.imageloader.ImageLoaderManager;
import com.wlwoon.imageloader.ImageLoaderOptions;
import com.wlwoon.imageloader.OnProgressListener;
import com.wlwoon.network.RetrofitFactory;
import com.wlwoon.workbook.hencode.HencodeActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity implements ActivityForResultCallback {


    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.btn_jump)
    Button mBtnJump;
    Button mButton;
    @BindView(R.id.iv)
    ImageView mIv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    int count = 0;

    boolean change = false;

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {
        mTvTip = findViewById(R.id.tv_tip);
        mBtnJump = findViewById(R.id.btn_jump);
        mButton = findViewById(R.id.btn_bg);
        View view = findViewById(R.id.view);
        String url2 = "https://static.dingtalk.com/media/lALPD4d8rJv-JsPNAY3NA0c_839_397.png_720x720q90g.jpg";
        String url = "https://static.dingtalk.com/media/lALPDhYBNXeY0XTNBJTNB8A_1984_1172.png_720x720q90g.jpg";
        String gif = "http://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2019-6/201961118291584771.gif";
        String url3 = "https://static.dingtalk.com/media/lALPBGY17Ifv6hbNBDjNB4A_1920_1080.png_720x720q90g.jpg";
        String url4 = "https://static.dingtalk.com/media/lALPBGnDayncPpHNAQHNA5g_920_257.png_720x720q90g.jpg";

        getPermission();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.printf("年-月-日 HH:MM:SS 格式：%tF %<tT%n", new Date());
//                loadPic(gif);

                Intent intent =  new Intent(Settings.ACTION_SETTINGS);

                startActivity(intent);
//                wifi();
//                playRecord();

//                getContact();
//                getData();

//                RxjavaDemo.getInstance().demoConcat();


//                Intent intent = new Intent(mContext, ContactsPickActivity.class);
//                intent.setComponent(new ComponentName("com.wlwoon.contactspicker", "com.wlwoon.contactspicker.ContactsPickActivity"));
//                startActivity(intent);

//                TextView textView = new TextView(mContext);
//                textView.setTextSize(20);
//                textView.setTextColor(getResources().getColor(R.color.colorAccent));
//                textView.setText("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻");
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                textView.setLayoutParams(params);
//                ViewGroup rootView = (ViewGroup) mTvTip.getRootView();
//                int left = mButton.getLeft();
//                int top = mButton.getTop();
//                int right = mButton.getRight();
//                int bottom = mButton.getBottom();
//                int height = textView.getHeight();
//                int width = textView.getWidth();
//                textView.layout(left, bottom, left + 50, bottom + 50);
//                rootView.addView(textView, params);
//
//                Log.i("wu.xy", "he:" + height + "width=" + width);


//                change = !change;
//                if (change) {
//                    view.setVisibility(View.VISIBLE);
//                } else {
//                    view.setVisibility(View.GONE);
//                }
            }
        });

        mBtnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.getInstance().showLong("text" + ++count);
//                Bundle bundle = new Bundle();
//                bundle.putString("text", "我来自mainactivity");
//                startActivityForResultWithData(mContext, MainActivity2.class, bundle, 100, MainActivity.this);
                startActivity(mContext, HencodeActivity.class);
            }
        });
        mTvTip.append("有响应");
    }

    private void getPermission() {
//        Permission
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED

                || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CHANGE_WIFI_STATE, android.Manifest.permission.WRITE_SETTINGS, Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        } else {
//            initDatas();
        }
    }

    private void loadPic(String gif) {
        ImageLoaderManager
                .getInstance()
                .showImage(
                        new ImageLoaderOptions
                                .Builder(mIv, gif)
//                                .isSkipMemoryCache(false)
//                                .placeholder()
                                .setOnProgressListener(new OnProgressListener() {
                                    @Override
                                    public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception) {
                                        if (totalBytes == 0) {
                                            return;
                                        }
                                        if (isDone) {
                                            ToastUtil.getInstance().showShort("图片加载完成");
                                        }
                                        String format = String.format("wxy 加载进度：%.2f %% %n", bytesRead * 100f / totalBytes);
                                        System.out.println(format);
                                    }
                                })
                                .build());
    }

    private void getContact() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isMultipleChoice", true);
        String[] permission = Utils.checkPermission(Permissions.CONTACTS);
        if (permission.length == 0) {
            startActivityForResultWithData(mContext, ContactsPickActivity.class, bundle, 101, this);
        } else {
            ActivityCompat.requestPermissions(mActivity, permission, PermissionCode.CONTACTS);
        }
    }

    @Override
    public void result(Intent intent, int code) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
//            String text = extras.getString("text");
            ArrayList<Contact> listContacts = extras.getParcelableArrayList("listContacts");
            mTvTip.append(new Gson().toJson(listContacts));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            switch (requestCode) {
                case PermissionCode.CONTACTS:
                    getContact();
                    break;
                case PermissionCode.MICROPHONE:
                    playRecord();
                    break;
            }

        } else {
            ToastUtil.getInstance().showLong("权限被拒绝了===");
        }
    }

    private void playRecord() {
        String[] permission = Utils.checkPermission(Permissions.MICROPHONE);
        if (permission.length == 0) {
            MediaPlayer player = MediaPlayer.create(this, R.raw.jihuo);
            player.start();
        } else {
            ActivityCompat.requestPermissions(this, permission, PermissionCode.MICROPHONE);
        }
    }

    //    @RequiresApi(api = Build.VERSION_CODES.P)
//    private void getExcutor() {
    @SuppressLint("CheckResult")
    private void getData() {
        Observable<DemoData> dataObservable = RetrofitFactory.getInstance().creat(CommonApi.class, Constance.url).getData();
        dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DemoData>() {
                    @Override
                    public void accept(DemoData demoData) throws Exception {
                        Logger.json(new Gson().toJson(demoData));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.i(throwable.getMessage());
                    }
                });


        Observable<Object> objectObservable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

            }
        }).observeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
    WifiManager wifiManager;
    void wifi() {
        wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
//        setWifiApEnabledForAndroidO(mContext,true);
        openHotspot("wxy","88888888");
    }

    private void setWifiEnable(WifiManager wifiManager) {
        try {
            Method method = wifiManager.getClass().getMethod("getWifiApState");
//            wifiState = ((int) method.invoke(wifiManager));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 创建WiFi热点
    public void openHotspot(String SSID, String password) {
        // 关闭WiFi
//        closeWifi();
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = SSID;
        config.preSharedKey = password;
        config.hiddenSSID = true;
        //开放系统认证
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        // 加密方式
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
        try {
            // 通过反射来打开热点
            Method method = wifiManager.getClass().getDeclaredMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            boolean enable = (Boolean) method.invoke(wifiManager, config, true);
            if (enable) Log.i(TAG, "热点已开启 SSID:" + SSID + " password:" + password);
            else Log.e(TAG, "创建热点失败");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "创建热点失败:" + e.getMessage());
        }
    }
    // 关闭WiFi热点
    public void closeHotspot() {
        try {
            Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");
            method.setAccessible(true);
            WifiConfiguration config = (WifiConfiguration) method.invoke(wifiManager);
            Method method2 = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method2.invoke(wifiManager, config, false);
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public static void setWifiApEnabledForAndroidO(Context context, boolean isEnable) {
        ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        Field iConnMgrField = null;
        try {
            iConnMgrField = connManager.getClass().getDeclaredField("mService");
            iConnMgrField.setAccessible(true);
            Object iConnMgr = iConnMgrField.get(connManager);
            Class<?> iConnMgrClass = Class.forName(iConnMgr.getClass().getName());

            if (isEnable) {
                Method startTethering = iConnMgrClass.getMethod("startTethering", int.class, ResultReceiver.class, boolean.class);
                startTethering.invoke(iConnMgr, 0, null, true);
            } else {
                Method startTethering = iConnMgrClass.getMethod("stopTethering", int.class);
                startTethering.invoke(iConnMgr, 0);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //成功
    private void getWifiState(WifiManager wifiManager) {
        int wifiState=0;
        try {
            Method method = wifiManager.getClass().getMethod("getWifiApState");
            wifiState = ((int) method.invoke(wifiManager));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("wxy","state "+wifiState);
    }


}