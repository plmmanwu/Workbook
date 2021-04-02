package com.wlwoon.workbook.share;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.wlwoon.base.BaseActivity;
import com.wlwoon.workbook.App;
import com.wlwoon.workbook.R;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShareShowActivity extends BaseActivity {

    @BindView(R.id.rc)
    RecyclerView mRc;
    @BindView(R.id.btn_show)
    Button mBtnShow;
    private ShareShowAdapter mAdapter;
    private ShareInfoDao mShareInfoDao;
    List<ShareInfo> mShareInfoList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_show;
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {
        DaoSession daoSession = App.getDaoSession();
        mShareInfoDao = daoSession.getShareInfoDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mShareInfoList = mShareInfoDao.queryBuilder().orderDesc(ShareInfoDao.Properties.SharePercent).build().list();
                mBtnShow.setText(mShareInfoList.size()+"");
                Log.d("wxy ==", mShareInfoList.size() + "");
            }
        }).start();
//        mRc.setLayoutManager(new GridLayoutManager(this,4));
        mRc.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShareShowAdapter(mShareInfoList);
        mRc.setAdapter(mAdapter);

    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.btn_show)
    public void onClick() {
        mAdapter.addDatas(mShareInfoList);
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Exception {

                emitter.onNext("xxxxxxxxxx");
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        Log.d("wxy ==",o.toString());
                        mBtnShow.setText(o.toString());

                    }
                });
    }
}