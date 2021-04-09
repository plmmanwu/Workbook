package com.wlwoon.workbook.share;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wlwoon.base.BaseActivity;
import com.wlwoon.workbook.App;
import com.wlwoon.workbook.DeviceUtils;
import com.wlwoon.workbook.LineChartDialog;
import com.wlwoon.workbook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.widget.ListPopupWindow;
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

public class ShareShowActivity extends BaseActivity implements ShareShowAdapter.OnClickNameListener {

    @BindView(R.id.rc)
    RecyclerView mRc;
    @BindView(R.id.btn_show)
    Button mBtnShow;
    @BindView(R.id.cav)
    CalendarView mCav;
    @BindView(R.id.search)
    EditText mSearch;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;
    @BindView(R.id.iv_del)
    ImageView mIvDel;
    private ShareShowAdapter mAdapter;
    private ShareInfoDao mShareInfoDao;
    List<ShareInfo> mShareInfoList;
    private ArrayAdapter<String> mArrayAdapter;
    private ListPopupWindow mListPopupWindow;
    private List<String> mShowList = new ArrayList<>();
    private boolean hasInput = false;
    private List<ShareInfo> mInfoList;
    private ShareInfo info;

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
        initEdt();
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

//        mCav.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
////                long date1 = view.getDate();
////                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
////                time = dateFormat.format(date1);
//                month += 1;
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(year);
//                stringBuilder.append("/");
//                if (String.valueOf(month).length() == 1) {
//                    stringBuilder.append("0");
//                }
//                stringBuilder.append(month);
//                stringBuilder.append("/");
//                if (String.valueOf(dayOfMonth).length() == 1) {
//                    stringBuilder.append("0");
//                }
//                stringBuilder.append(dayOfMonth);
//                time = stringBuilder.toString();
//                Log.d("wxy ==qureyDatas ==", time);
//                qureyDatas();
//            }
//        });

    }

    private void initEdt() {
        mArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, mShowList);
        mListPopupWindow = new ListPopupWindow(mContext);
        mListPopupWindow.setAdapter(mArrayAdapter);
        int deviceWidth = DeviceUtils.deviceWidth(mContext);
        mListPopupWindow.setWidth(deviceWidth / 2);
        int i = DeviceUtils.deviceHeight(mContext);
        mListPopupWindow.setHeight(i / 3);
        mListPopupWindow.setAnchorView(mSearch);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
//            mListPopupWindow.setModal(true);//设置是否是模式
        mListPopupWindow.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NEEDED);
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String name = mShowList.get(position);
                info = mInfoList.get(position);
                qureyDatas(info.getShareId());
                mSearch.setText(name);
                mListPopupWindow.dismiss();
                mSearch.setSelection(name.length());
                hideKeybord();
            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mListPopupWindow.isShowing()) {
                    mListPopupWindow.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NEEDED);
                }
            }
        });

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mListPopupWindow.isShowing()) {
                    mListPopupWindow.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NEEDED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    hasInput = false;
                    if (mListPopupWindow != null && mListPopupWindow.isShowing()) {
                        mListPopupWindow.dismiss();
                        hideKeybord();
                        qureyDatas();
                    }
                } else {
                    hasInput = true;
                    //输入内容非空的时候才开始搜索
                    startSearch(s.toString());
                }

            }
        });
    }

    private void qureyDatas(String shareId) {
        mInfoList = mShareInfoDao.queryBuilder().where(ShareInfoDao.Properties.ShareId.eq(shareId)).orderDesc(ShareInfoDao.Properties.Time).build().list();
        mAdapter.addDatas(mInfoList);
    }

    private void hideKeybord() {
        //隐藏键盘
        InputMethodManager manager = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        if (manager.isActive()) {
            manager.hideSoftInputFromWindow(mSearch.getApplicationWindowToken(), 0);
        }
    }

    void startSearch(String cell) {
        mInfoList = mShareInfoDao.queryBuilder().where(ShareInfoDao.Properties.Date.eq("2021/04/01")).whereOr(ShareInfoDao.Properties.ShareName.like("%" + cell + "%"), ShareInfoDao.Properties.ShareId.like("%" + cell + "%")).build().list();
        mShowList.clear();
        if (mInfoList != null && mInfoList.size() > 0) {
            for (ShareInfo shareInfo : mInfoList) {
                mShowList.add(shareInfo.shareName);
            }
        }
        if (!mListPopupWindow.isShowing()) {
            mListPopupWindow.show();
        }
        mArrayAdapter.notifyDataSetChanged();

    }

    void qureyDatas() {

        Observable.create(new ObservableOnSubscribe<List<ShareInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ShareInfo>> emitter) throws Exception {
                mShareInfoList = mShareInfoDao.queryBuilder().where(ShareInfoDao.Properties.Date.eq("2021/04/01")).orderDesc(ShareInfoDao.Properties.SharePercent).build().list();
                emitter.onNext(mShareInfoList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<ShareInfo>>() {
                    @Override
                    public void accept(List<ShareInfo> o) throws Exception {
                        mAdapter.addDatas(o);
                    }
                });

    }

    private void initDb() {
//        mRc.setLayoutManager(new GridLayoutManager(this,4));

        mRc.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShareShowAdapter(mShareInfoList);
        mRc.setAdapter(mAdapter);
        mAdapter.setOnClickNameListener(this);
        qureyDatas();

    }

    @OnClick({R.id.btn_show,R.id.iv_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                break;
            case R.id.iv_del:
                mSearch.setText("");
                qureyDatas();
                break;
        }
    }

    @Override
    public void clickName(ShareInfo shareInfo) {
        String shareId = shareInfo.getShareId();
        mShareInfoList = mShareInfoDao.queryBuilder().where(ShareInfoDao.Properties.ShareId.eq(shareId)).orderDesc(ShareInfoDao.Properties.Time).build().list();

        LineChartDialog dialog = new LineChartDialog(this);
        dialog.setData(mShareInfoList);
    }

    @Override
    public void clickCode(ShareInfo shareInfo) {
        String shareId = shareInfo.getShareId();
        mSearch.setText(shareId);
//        mShareInfoList = mShareInfoDao.queryBuilder().where(ShareInfoDao.Properties.ShareId.eq(shareId)).orderDesc(ShareInfoDao.Properties.Time).build().list();
    }
}