package com.wlwoon.workbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wlwoon.base.BaseActivity;

import butterknife.BindView;

public class MainActivity2 extends BaseActivity {


    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.btn_finish)
    Button mBtnFinish;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {
        mTvTip = findViewById(R.id.tv_tip);
        mBtnFinish = findViewById(R.id.btn_finish);
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("text", "我结束了自己");
                finishActivityWithData(bundle);
            }
        });
        String text = extras.getString("text");
        mTvTip.append(text);
    }
}