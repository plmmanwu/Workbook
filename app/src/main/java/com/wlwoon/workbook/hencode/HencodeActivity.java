package com.wlwoon.workbook.hencode;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.wlwoon.base.BaseActivity;
import com.wlwoon.workbook.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class HencodeActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    static List<Fragment> mFragments = new ArrayList<>();
    static List<String> titles = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hencode;
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {

        titles.add("drawColor");
        titles.add("drawCircle");
        titles.add("drawRect");
        titles.add("drawArc");
        mFragments.add(HencodeFragment.getInstance(titles.get(0)));
        mFragments.add(HencodeFragment.getInstance(titles.get(1)));
        mFragments.add(HencodeFragment.getInstance(titles.get(2)));
        mFragments.add(HencodeFragment.getInstance(titles.get(3)));


        mTablayout.setSelectedTabIndicatorColor(Color.RED);
        mTablayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
        mTablayout.setTabTextColors(Color.GRAY,Color.BLACK);
        mTablayout.setupWithViewPager(mViewpager);
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
    }

}