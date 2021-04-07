package com.wlwoon.workbook.share;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wlwoon.base.BaseActivity;
import com.wlwoon.base.common.Permissions;
import com.wlwoon.base.common.Utils;
import com.wlwoon.network.RetrofitFactory;
import com.wlwoon.workbook.App;
import com.wlwoon.workbook.CommonApi;
import com.wlwoon.workbook.Constance;
import com.wlwoon.workbook.R;

import org.greenrobot.greendao.AbstractDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ShareActivity2 extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    String url = "http://www.hkexnews.hk/sdw/search/mutualmarket_c.aspx?t=sh&t=sh";
    @BindView(R.id.btn_get)
    Button mBtnGet;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.edt_data)
    EditText mEdtData;
    @BindView(R.id.btn_show)
    Button mBtnShow;
    @BindView(R.id.rg)
    RadioGroup mRg;


    private ShareInfoDao mShareInfoDao;
    private ShareInfo2Dao mShareInfo2Dao;
    private ShareInfo5Dao mShareInfo5Dao;
    private ShareInfo10Dao mShareInfo10Dao;
    private ShareInfo20Dao mShareInfo20Dao;
    private ShareInfo30Dao mShareInfo30Dao;

    AbstractDao mDao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {

        mRg.setOnCheckedChangeListener(this);
        Date date = new Date(System.currentTimeMillis()-1*24*60*60*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        time = dateFormat.format(date);
        initDB();

    }

//    private void getHtmlContent() {
//        try {
//            Document doc = Jsoup.connect(url).get();
//            parseHtml(doc);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    int qCount = 2;

    /**
     * 创业板：创业板的代码是300打头的股票代码。
     * <p>
     * 　　沪市A股：沪市A股的代码是以600、601或603打头。
     * <p>
     * 　　沪市B股：沪市B股的代码是以900打头。
     * <p>
     * 　　深市A股：深市A股的代码是以000打头。
     * <p>
     * 　　中小板：中小板的代码是002打头。
     * <p>
     * 　　深圳B股：深圳B股的代码是以200打头。
     * <p>
     * 　　新股申购：沪市新股申购的代码是以730打头。深市新股申购的代码与深市股票买卖代码一样。
     * <p>
     * 　　配股代码：沪市以700打头，深市以080打头权证，沪市是580打头深市是031打头。
     * <p>
     * 30---688 科创
     * 90---600
     * 91---601
     * 93---603
     * 95---605 新
     * <p>
     * <p>
     * 70---000
     * 71---001
     * 72---002
     * 73---003 新
     * 77---300  创业
     *
     * @param document
     */


    Map<String, String> mMap = new HashMap<>();

    @SuppressLint("CheckResult")
    private void getDatas(String t) {

        RequestBody requestBody = getRequestBody();
        Observable<String> dataObservable = RetrofitFactory.getInstance().creat2(CommonApi.class, Constance.shareUrl).getShareData(t, requestBody);
        dataObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("wxy ==", s);
                        Document document = Jsoup.parse(s);
                        parseData(document);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d("wxy ==", throwable.getMessage());
                    }
                });
    }

    private void parseData(Document document) {
        if (mDao instanceof ShareInfoDao) {
            parseHtml(document,mShareInfoDao);
        } else if (mDao instanceof ShareInfo2Dao) {
            parseHtml2(document,mShareInfo2Dao);
        } else if (mDao instanceof ShareInfo5Dao) {
            parseHtml5(document,mShareInfo5Dao);
        } else if (mDao instanceof ShareInfo10Dao) {
            parseHtml10(document,mShareInfo10Dao);
        } else if (mDao instanceof ShareInfo20Dao) {
            parseHtml20(document,mShareInfo20Dao);
        } else if (mDao instanceof ShareInfo30Dao) {
            parseHtml30(document,mShareInfo30Dao);
        }
    }

    public RequestBody getRequestBody() {
//        mMap.put("__VIEWSTATE","/wEPDwUJNjIxMTYzMDAwZGQ79IjpLOM+JXdffc28A8BMMA9+yg==");
        mMap.put("__VIEWSTATE", "%2FwEPDwUJNjIxMTYzMDAwZGQ79IjpLOM%2BJXdffc28A8BMMA9%2Byg%3D%3D");
        mMap.put("__VIEWSTATEGENERATOR", "EC4ACD6F");
        mMap.put("__EVENTVALIDATION", "%2FwEdAAdtFULLXu4cXg1Ju23kPkBZVobCVrNyCM2j%2BbEk3ygqmn1KZjrCXCJtWs9HrcHg6Q64ro36uTSn%2FZ2SUlkm9HsG7WOv0RDD9teZWjlyl84iRMtpPncyBi1FXkZsaSW6dwqO1N1XNFmfsMXJasjxX85jz8PxJxwgNJLTNVe2Bh%2Fbcg5jDf8%3D");
//        mMap.put("__EVENTVALIDATION","/wEdAAdtFULLXu4cXg1Ju23kPkBZVobCVrNyCM2j+bEk3ygqmn1KZjrCXCJtWs9HrcHg6Q64ro36uTSn/Z2SUlkm9HsG7WOv0RDD9teZWjlyl84iRMtpPncyBi1FXkZsaSW6dwqO1N1XNFmfsMXJasjxX85jz8PxJxwgNJLTNVe2Bh/bcg5jDf8=");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(date);
        mMap.put("today", format);
        mMap.put("sortBy", Constance.sortById);
        mMap.put("sortDirection", Constance.sortUp);
        mMap.put("txtShareholdingDate", time);
        mMap.put("btnSearch", Constance.btnSearch);

        StringBuffer data = new StringBuffer();
        if (mMap != null && mMap.size() > 0) {
            Iterator iter = mMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                data.append(key).append("=").append(val).append("&");
            }
        }
        String jso = data.substring(0, data.length() - 1);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), jso);

        return requestBody;
    }

    private void initDB() {
        DaoSession daoSession = App.getDaoSession();
        mShareInfoDao = daoSession.getShareInfoDao();
        mShareInfo2Dao = daoSession.getShareInfo2Dao();
        mShareInfo5Dao = daoSession.getShareInfo5Dao();
        mShareInfo10Dao = daoSession.getShareInfo10Dao();
        mShareInfo20Dao = daoSession.getShareInfo20Dao();
        mShareInfo30Dao = daoSession.getShareInfo30Dao();

    }

    @OnClick({R.id.btn_get, R.id.btn_show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                qCount = 2;
                getDatas("sh");
                mTv.setText("开始更新---");
                break;
            case R.id.btn_show:
                String[] strings = Utils.checkPermission(Permissions.STORAGE);
                if (strings.length > 0) {
                    ActivityCompat.requestPermissions(this, strings, 101);
                } else{
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", index);
                    startActivityWithData(mContext, ShareShowActivity.class,bundle);
                }

                break;
        }
    }


    String time = "";

    int index=0;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb:
                mDao = mShareInfoDao;
                Date date = new Date(System.currentTimeMillis()-1*24*60*60*1000);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                time = dateFormat.format(date);
                index = 0;
                break;
            case R.id.rb2:
                mDao = mShareInfo2Dao;
                Date date2 = new Date(System.currentTimeMillis()-2*24*60*60*1000);
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
                time = dateFormat2.format(date2);
                index = 1;
                break;
            case R.id.rb5:
                mDao = mShareInfo5Dao;
                Date date5 = new Date(System.currentTimeMillis()-5*24*60*60*1000);
                SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyyy/MM/dd");
                time = dateFormat5.format(date5);
                index = 2;
                break;
            case R.id.rb10:
                mDao = mShareInfo10Dao;
                Date date10 = new Date(System.currentTimeMillis()-10*24*60*60*1000);
                SimpleDateFormat dateFormat10 = new SimpleDateFormat("yyyy/MM/dd");
                time = dateFormat10.format(date10);
                index = 3;
                break;
            case R.id.rb20:
                mDao = mShareInfo20Dao;
                Date date20 = new Date(System.currentTimeMillis()-20*24*60*60*1000);
                SimpleDateFormat dateFormat20 = new SimpleDateFormat("yyyy/MM/dd");
                time = dateFormat20.format(date20);
                index = 4;
                break;
            case R.id.rb30:
                mDao = mShareInfo30Dao;
                Date date30 = new Date(System.currentTimeMillis()-30*24*60*60*1000);
                SimpleDateFormat dateFormat30 = new SimpleDateFormat("yyyy/MM/dd");
                time = dateFormat30.format(date30);
                index = 5;
                break;

        }
    }

    void parseHtml(Document document,ShareInfoDao dao) {
        Elements elements = document.select("table[id=mutualmarket-result] > tbody > tr");
        List<ShareInfo> list = new ArrayList<>();
        ShareInfo data;
        int count = 0;
        for (Element element : elements) {
            data = new ShareInfo();
            String text1 = element.select("td[class=col-stock-code] > div[class=mobile-list-body]").text();
            if (text1.startsWith("30")) {
                text1 = text1.replaceFirst("30", "688");
                continue;
            } else if (text1.startsWith("9")) {
                text1 = text1.replaceFirst("9", "60");
            } else if (text1.startsWith("77")) {
                text1 = text1.replaceFirst("77", "300");
                continue;
            } else if (text1.startsWith("7")) {
                text1 = text1.replaceFirst("7", "00");
            } else {
                continue;
            }
            data.setShareId(text1);//开奖期数
            data.setShareName(element.select("td[class=col-stock-name] > div[class=mobile-list-body]").text());//开奖日期
            String text2 = element.select("td[class=col-shareholding] > div[class=mobile-list-body]").text();
            String replace1 = text2.replace(",", "");
            long l = Long.parseLong(replace1);
            data.setShareNum(l);//开奖日期
            String text = element.select("td[class=col-shareholding-percent] > div[class=mobile-list-body]").text();
            String replace = text.replace("%", "");
            double v = Double.parseDouble(replace);
            data.setSharePercent(v);//开奖日期
            dao.insertOrReplace(data);
            if (count == 0) {
                mTv.setText(data.toString());
                Log.d("wxy " + count++, data.toString());
            }
        }
        if (--qCount > 0) {
            getDatas("sz");
            return;
        }
        mTv.setText("更新完毕");
    }

    void parseHtml2(Document document,ShareInfo2Dao dao) {
        Elements elements = document.select("table[id=mutualmarket-result] > tbody > tr");
        List<ShareInfo2> list = new ArrayList<>();
        ShareInfo2 data;
        int count = 0;
        for (Element element : elements) {
            data = new ShareInfo2();
            String text1 = element.select("td[class=col-stock-code] > div[class=mobile-list-body]").text();
            if (text1.startsWith("30")) {
                text1 = text1.replaceFirst("30", "688");
                continue;
            } else if (text1.startsWith("9")) {
                text1 = text1.replaceFirst("9", "60");
            } else if (text1.startsWith("77")) {
                text1 = text1.replaceFirst("77", "300");
                continue;
            } else if (text1.startsWith("7")) {
                text1 = text1.replaceFirst("7", "00");
            } else {
                continue;
            }
            data.setShareId(text1);//开奖期数
            data.setShareName(element.select("td[class=col-stock-name] > div[class=mobile-list-body]").text());//开奖日期
            String text2 = element.select("td[class=col-shareholding] > div[class=mobile-list-body]").text();
            String replace1 = text2.replace(",", "");
            long l = Long.parseLong(replace1);
            data.setShareNum(l);//开奖日期
            String text = element.select("td[class=col-shareholding-percent] > div[class=mobile-list-body]").text();
            String replace = text.replace("%", "");
            double v = Double.parseDouble(replace);
            data.setSharePercent(v);//开奖日期
            dao.insertOrReplace(data);
            if (count == 0) {
                mTv.setText(data.toString());
                Log.d("wxy " + count++, data.toString());
            }
        }
        if (--qCount > 0) {
            getDatas("sz");
            return;
        }
        mTv.setText("更新完毕");
    }

    void parseHtml5(Document document,ShareInfo5Dao dao) {
        Elements elements = document.select("table[id=mutualmarket-result] > tbody > tr");
        List<ShareInfo2> list = new ArrayList<>();
        ShareInfo5 data;
        int count = 0;
        for (Element element : elements) {
            data = new ShareInfo5();
            String text1 = element.select("td[class=col-stock-code] > div[class=mobile-list-body]").text();
            if (text1.startsWith("30")) {
                text1 = text1.replaceFirst("30", "688");
                continue;
            } else if (text1.startsWith("9")) {
                text1 = text1.replaceFirst("9", "60");
            } else if (text1.startsWith("77")) {
                text1 = text1.replaceFirst("77", "300");
                continue;
            } else if (text1.startsWith("7")) {
                text1 = text1.replaceFirst("7", "00");
            } else {
                continue;
            }
            data.setShareId(text1);//开奖期数
            data.setShareName(element.select("td[class=col-stock-name] > div[class=mobile-list-body]").text());//开奖日期
            String text2 = element.select("td[class=col-shareholding] > div[class=mobile-list-body]").text();
            String replace1 = text2.replace(",", "");
            long l = Long.parseLong(replace1);
            data.setShareNum(l);//开奖日期
            String text = element.select("td[class=col-shareholding-percent] > div[class=mobile-list-body]").text();
            String replace = text.replace("%", "");
            double v = Double.parseDouble(replace);
            data.setSharePercent(v);//开奖日期
            dao.insertOrReplace(data);
            if (count == 0) {
                mTv.setText(data.toString());
                Log.d("wxy " + count++, data.toString());
            }
        }
        if (--qCount > 0) {
            getDatas("sz");
            return;
        }
        mTv.setText("更新完毕");
    }

    void parseHtml10(Document document,ShareInfo10Dao dao) {
        Elements elements = document.select("table[id=mutualmarket-result] > tbody > tr");
        List<ShareInfo10> list = new ArrayList<>();
        ShareInfo10 data;
        int count = 0;
        for (Element element : elements) {
            data = new ShareInfo10();
            String text1 = element.select("td[class=col-stock-code] > div[class=mobile-list-body]").text();
            if (text1.startsWith("30")) {
                text1 = text1.replaceFirst("30", "688");
                continue;
            } else if (text1.startsWith("9")) {
                text1 = text1.replaceFirst("9", "60");
            } else if (text1.startsWith("77")) {
                text1 = text1.replaceFirst("77", "300");
                continue;
            } else if (text1.startsWith("7")) {
                text1 = text1.replaceFirst("7", "00");
            } else {
                continue;
            }
            data.setShareId(text1);//开奖期数
            data.setShareName(element.select("td[class=col-stock-name] > div[class=mobile-list-body]").text());//开奖日期
            String text2 = element.select("td[class=col-shareholding] > div[class=mobile-list-body]").text();
            String replace1 = text2.replace(",", "");
            long l = Long.parseLong(replace1);
            data.setShareNum(l);//开奖日期
            String text = element.select("td[class=col-shareholding-percent] > div[class=mobile-list-body]").text();
            String replace = text.replace("%", "");
            double v = Double.parseDouble(replace);
            data.setSharePercent(v);//开奖日期
            dao.insertOrReplace(data);
            if (count == 0) {
                mTv.setText(data.toString());
                Log.d("wxy " + count++, data.toString());
            }
        }
        if (--qCount > 0) {
            getDatas("sz");
            return;
        }
        mTv.setText("更新完毕");
    }

    void parseHtml20(Document document,ShareInfo20Dao dao) {
        Elements elements = document.select("table[id=mutualmarket-result] > tbody > tr");
        List<ShareInfo20> list = new ArrayList<>();
        ShareInfo20 data;
        int count = 0;
        for (Element element : elements) {
            data = new ShareInfo20();
            String text1 = element.select("td[class=col-stock-code] > div[class=mobile-list-body]").text();
            if (text1.startsWith("30")) {
                text1 = text1.replaceFirst("30", "688");
                continue;
            } else if (text1.startsWith("9")) {
                text1 = text1.replaceFirst("9", "60");
            } else if (text1.startsWith("77")) {
                text1 = text1.replaceFirst("77", "300");
                continue;
            } else if (text1.startsWith("7")) {
                text1 = text1.replaceFirst("7", "00");
            } else {
                continue;
            }
            data.setShareId(text1);//开奖期数
            data.setShareName(element.select("td[class=col-stock-name] > div[class=mobile-list-body]").text());//开奖日期
            String text2 = element.select("td[class=col-shareholding] > div[class=mobile-list-body]").text();
            String replace1 = text2.replace(",", "");
            long l = Long.parseLong(replace1);
            data.setShareNum(l);//开奖日期
            String text = element.select("td[class=col-shareholding-percent] > div[class=mobile-list-body]").text();
            String replace = text.replace("%", "");
            double v = Double.parseDouble(replace);
            data.setSharePercent(v);//开奖日期
            dao.insertOrReplace(data);
            if (count == 0) {
                mTv.setText(data.toString());
                Log.d("wxy " + count++, data.toString());
            }
        }
        if (--qCount > 0) {
            getDatas("sz");
            return;
        }
        mTv.setText("更新完毕");
    }

    void parseHtml30(Document document,ShareInfo30Dao dao) {
        Elements elements = document.select("table[id=mutualmarket-result] > tbody > tr");
        List<ShareInfo30> list = new ArrayList<>();
        ShareInfo30 data;
        int count = 0;
        for (Element element : elements) {
            data = new ShareInfo30();
            String text1 = element.select("td[class=col-stock-code] > div[class=mobile-list-body]").text();
            if (text1.startsWith("30")) {
                text1 = text1.replaceFirst("30", "688");
                continue;
            } else if (text1.startsWith("9")) {
                text1 = text1.replaceFirst("9", "60");
            } else if (text1.startsWith("77")) {
                text1 = text1.replaceFirst("77", "300");
                continue;
            } else if (text1.startsWith("7")) {
                text1 = text1.replaceFirst("7", "00");
            } else {
                continue;
            }
            data.setShareId(text1);//开奖期数
            data.setShareName(element.select("td[class=col-stock-name] > div[class=mobile-list-body]").text());//开奖日期
            String text2 = element.select("td[class=col-shareholding] > div[class=mobile-list-body]").text();
            String replace1 = text2.replace(",", "");
            long l = Long.parseLong(replace1);
            data.setShareNum(l);//开奖日期
            String text = element.select("td[class=col-shareholding-percent] > div[class=mobile-list-body]").text();
            String replace = text.replace("%", "");
            double v = Double.parseDouble(replace);
            data.setSharePercent(v);//开奖日期
            dao.insertOrReplace(data);
            if (count == 0) {
                mTv.setText(data.toString());
                Log.d("wxy " + count++, data.toString());
            }
        }
        if (--qCount > 0) {
            getDatas("sz");
            return;
        }
        mTv.setText("更新完毕");
    }
}