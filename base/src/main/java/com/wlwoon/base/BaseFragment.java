package com.wlwoon.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wxy on 2020/4/27.
 */
public abstract class BaseFragment extends Fragment {
    protected Context ctx;
    private Unbinder mBind;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBind = ButterKnife.bind(this,view);
        init(savedInstanceState,getArguments());
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState,Bundle arguments);

    protected abstract int getLayoutId();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
    }
}
