package com.wlwoon.workbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.wlwoon.network.RetrofitFactory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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
        String url2 = "http://dingyue.ws.126.net/aV3dMfrhDdj5YbuRtTZ19sYLyRUlMv2kSkuoC2JFjpHob1543285491837compressflag.jpg";
        String gif = "http://upfile.asqql.com/2009pasdfasdfic2009s305985-ts/2019-6/201961118291584771.gif";
        ImageLoaderManager
                .getInstance()
                .showImage(
                        new ImageLoaderOptions
                                .Builder(mIv, gif)
//                                .placeholder()
                                .build());

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData();

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
                ToastUtil.getInstance().showLong("text" + ++count);
                Bundle bundle = new Bundle();
                bundle.putString("text", "我来自mainactivity");
                startActivityForResultWithData(mContext, MainActivity2.class, bundle, 100, MainActivity.this);
            }
        });
        mTvTip.append("有响应");
    }

    private void getContact() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isMultipleChoice", true);
        String[] permission = Utils.checkPermission(Permissions.CONTACTS);
        if (permission.length == 0) {
            startActivityForResultWithData(mContext, ContactsPickActivity.class,bundle,101,this);
        } else {
            ActivityCompat.requestPermissions(mActivity,permission, PermissionCode.CONTACTS);
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
            getContact();
        } else {
            ToastUtil.getInstance().showLong("权限被拒绝了===");
        }
    }

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

}