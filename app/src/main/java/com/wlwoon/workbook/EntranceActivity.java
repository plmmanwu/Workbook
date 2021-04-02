package com.wlwoon.workbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wlwoon.base.BaseActivity;
import com.wlwoon.workbook.share.ShareActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EntranceActivity extends BaseActivity {

    @BindView(R.id.btn_chart)
    Button mBtnChart;
    @BindView(R.id.btn_chart2)
    Button mBtnChart2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_entrance;
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {

    }

    @OnClick({R.id.btn_chart,R.id.btn_chart2})
    public void click(View view){

        switch (view.getId()){
            case R.id.btn_chart2:
                startActivity(this, ShareActivity.class);
                break;
            case R.id.btn_chart:
                startActivity(this,LineBarChartActivity.class);
                break;
        }

    }


}