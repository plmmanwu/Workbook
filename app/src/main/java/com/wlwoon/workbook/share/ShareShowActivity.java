package com.wlwoon.workbook.share;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;

import com.wlwoon.base.BaseActivity;
import com.wlwoon.workbook.App;
import com.wlwoon.workbook.LineChartDialog;
import com.wlwoon.workbook.R;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ShareShowActivity extends BaseActivity implements ShareShowAdapter.OnClickNameListener {

    @BindView(R.id.rc)
    RecyclerView mRc;
    @BindView(R.id.btn_show)
    Button mBtnShow;
    @BindView(R.id.cav)
    CalendarView mCav;
    private ShareShowAdapter mAdapter;
    private ShareInfoDao mShareInfoDao;
    List<ShareInfo> mShareInfoList;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_show;
    }

    String time = "";

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {
        DaoSession daoSession = App.getDaoSession();
        mShareInfoDao = daoSession.getShareInfoDao();
        initDb();
        int index = extras.getInt("index");
        Date date = new Date(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        time = dateFormat.format(date);
        switch (index) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }

        initDb();

        mCav.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@androidx.annotation.NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                long date1 = view.getDate();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//                time = dateFormat.format(date1);
                month += 1;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(year);
                stringBuilder.append("/");
                if (String.valueOf(month).length()==1) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(month);
                stringBuilder.append("/");
                if (String.valueOf(dayOfMonth).length()==1) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(dayOfMonth);
                time = stringBuilder.toString();
                Log.d("wxy ==qureyDatas ==", time);
                qureyDatas();
            }
        });

    }

    void qureyDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mShareInfoList = mShareInfoDao.queryBuilder().where(ShareInfoDao.Properties.Date.eq(time)).orderDesc(ShareInfoDao.Properties.SharePercent).build().list();
//                mShareInfoList = mShareInfoDao.queryBuilder().orderDesc(ShareInfoDao.Properties.SharePercent).build().list();
                mBtnShow.setText(mShareInfoList.size() + "");
                Log.d("wxy ==", mShareInfoList.size() + "");
            }
        }).start();
    }

    private void initDb() {
        qureyDatas();
//        mRc.setLayoutManager(new GridLayoutManager(this,4));

        mRc.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShareShowAdapter(mShareInfoList);
        mRc.setAdapter(mAdapter);
        mAdapter.setOnClickNameListener(this);

    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.btn_show)
    public void onClick() {
        if (mShareInfoList==null||mShareInfoList.size()==0) {
            return;
        }
//        mInfoList=mShareInfoList.get(0).getInfo();
        mBtnShow.setText(mShareInfoList.size()+"");
        Collections.sort(mShareInfoList, new Comparator<ShareInfo>() {
            @Override
            public int compare(ShareInfo o1, ShareInfo o2) {
                return ((int) ((o2.getSharePercent() - o1.getSharePercent()) * 100));
            }
        });
        mAdapter.addDatas(mShareInfoList);
    }

    @Override
    public void clickName(ShareInfo shareInfo) {
        String shareId = shareInfo.getShareId();
        mShareInfoList = mShareInfoDao.queryBuilder().where(ShareInfoDao.Properties.ShareId.eq(shareId)).orderDesc(ShareInfoDao.Properties.Time).build().list();
        mAdapter.addDatas(mShareInfoList);

        LineChartDialog dialog = new LineChartDialog(this);
        dialog.setData(mShareInfoList);
    }
}