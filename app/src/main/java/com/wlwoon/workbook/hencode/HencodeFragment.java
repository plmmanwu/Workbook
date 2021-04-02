package com.wlwoon.workbook.hencode;

import android.os.Bundle;
import android.widget.TextView;

import com.wlwoon.base.BaseFragment;
import com.wlwoon.workbook.R;

import butterknife.BindView;

/**
 * Created by wxy on 2020/12/24.
 */

public class HencodeFragment extends BaseFragment {

    static HencodeFragment mHencodeFragment;
    @BindView(R.id.tv)
    TextView mTv;

    @Override
    protected void init(Bundle savedInstanceState, Bundle arguments) {
        String txt = arguments.getString("txt");
        mTv.setText(txt);
    }

    public static HencodeFragment getInstance(String txt) {

        Bundle bundle = new Bundle();
        bundle.putString("txt", txt);
//        if (mHencodeFragment == null) {
            mHencodeFragment = new HencodeFragment();
//        }
        mHencodeFragment.setArguments(bundle);
        return mHencodeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hencode;
    }
}
