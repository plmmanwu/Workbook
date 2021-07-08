package com.wlwoon.workbook.video;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.wlwoon.workbook.R;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by wxy on 2021/4/15.
 */

public class TitleListAdapter extends BaseQuickAdapter<TitleBean, TitleListAdapter.VH> {


    public TitleListAdapter(@Nullable List<TitleBean> data) {
        super(R.layout.item_title_list, data);
    }

    @Override
    protected void convert(@NotNull VH vh, TitleBean name) {
        vh.mTvName.setText(name.getName());
        if (name.selected) {
            vh.mTvName.setTextColor(Color.BLUE);
        } else {
            vh.mTvName.setTextColor(Color.BLACK);
        }
    }

    class VH extends BaseViewHolder {

        private final TextView mTvName;

        public VH(@NotNull View view) {
            super(view);
            mTvName = view.findViewById(R.id.tv_name);
        }
    }
}
