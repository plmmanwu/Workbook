package com.wlwoon.workbook;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wlwoon.base.BaseActivity;
import com.wlwoon.workbook.customview.WFlowLayout;
import com.wlwoon.workbook.customview.WSignView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wxy on 2020/9/10.
 */

public class WidgetActivity extends BaseActivity {
    @BindView(R.id.btn_sign)
    Button mBtnSign;
    @BindView(R.id.btn_flow)
    Button mBtnFlow;
    @BindView(R.id.signview)
    WSignView mSignview;
    @BindView(R.id.flowlayout)
    WFlowLayout mFlowlayout;
    private List<String> list = new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_widget;
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {
        for (int i = 0; i <10; i++) {
            list.add("Android");
            list.add("Java");
            list.add("IOS");
            list.add("python");
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);

//        if (mFlowlayout != null) {
//            mFlowlayout.removeAllViews();
//        }
//        for (int i = 0; i < list.size(); i++) {
//            TextView tv = new TextView(this);
//            tv.setPadding(28, 10, 28, 10);
//            tv.setText(list.get(i));
//            tv.setMaxEms(10);
//            tv.setSingleLine();
////            tv.setBackgroundResource(R.drawable.selector_playsearch);
//            tv.setLayoutParams(layoutParams);
//            mFlowlayout.addView(tv, layoutParams);
//        }
    }


    @OnClick({R.id.btn_sign, R.id.btn_flow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign:
                mSignview.clean();
                break;
            case R.id.btn_flow:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
