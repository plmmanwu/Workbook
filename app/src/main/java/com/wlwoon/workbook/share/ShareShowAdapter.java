package com.wlwoon.workbook.share;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlwoon.workbook.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by wxy on 2021/4/2.
 */

public class ShareShowAdapter extends RecyclerView.Adapter<ShareShowAdapter.VH> {

    List<ShareInfo> list;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_percent)
    TextView mTvPercent;

    public ShareShowAdapter(List<ShareInfo> list) {
        if (list==null) {
            list = new ArrayList<>();
        }
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_show, parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ShareInfo shareInfo = list.get(position);
        holder.mTvCode.setText(shareInfo.getShareId());
        holder.tv_name.setText(shareInfo.getShareName());
        holder.tv_num.setText(shareInfo.getShareNum()+"");
        holder.tv_percent.setText(shareInfo.getSharePercent()+"");
        holder.tv_time.setText(shareInfo.getDate());

        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickNameListener!=null) {
                    mOnClickNameListener.clickName(shareInfo);
                }
            }
        });

        holder.mTvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickNameListener!=null) {
                    mOnClickNameListener.clickCode(shareInfo);
                }
            }
        });
    }

    public interface OnClickNameListener{
        void clickName(ShareInfo shareInfo);

        void clickCode(ShareInfo shareInfo);
    }

    OnClickNameListener mOnClickNameListener;

    public void setOnClickNameListener(OnClickNameListener onClickNameListener) {
        mOnClickNameListener = onClickNameListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addDatas(List<ShareInfo> subList) {
        list=subList;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {

        private final TextView mTvCode;
        private final TextView tv_name;
        private final TextView tv_num;
        private final TextView tv_time;
        private final TextView tv_percent;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTvCode = itemView.findViewById(R.id.tv_code);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_percent = itemView.findViewById(R.id.tv_percent);
        }
    }
}
