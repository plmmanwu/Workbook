package com.wlwoon.workbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wlwoon.base.BaseActivity;
import com.wlwoon.base.common.ToastUtil;
import com.wlwoon.base.interfaces.ActivityForResultCallback;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements ActivityForResultCallback {


    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.btn_jump)
    Button mBtnJump;
    Button mButton;

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
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView = new TextView(mContext);
                textView.setTextSize(20);
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                textView.setText("嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻");
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
                ViewGroup rootView = (ViewGroup) mTvTip.getRootView();
                int left = mButton.getLeft();
                int top = mButton.getTop();
                int right = mButton.getRight();
                int bottom = mButton.getBottom();
                int height = textView.getHeight();
                int width = textView.getWidth();
                textView.layout(left,bottom,left+50,bottom+50);
                rootView.addView(textView,params);

                Log.i("wu.xy", "he:" + height + "width=" + width);



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
                ToastUtil.getInstance().showLong("text"+ ++count);
                Bundle bundle = new Bundle();
                bundle.putString("text", "我来自mainactivity");
                startActivityForResultWithData(mContext, MainActivity2.class, bundle, 100, MainActivity.this);
            }
        });
        mTvTip.append("有响应");
    }

    @Override
    public void result(Intent intent,int code) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            String text = extras.getString("text");
            mTvTip.append(text);
        }
    }

}