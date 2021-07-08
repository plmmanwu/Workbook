package com.wlwoon.workbook.video;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wlwoon.workbook.DeviceUtils;
import com.wlwoon.workbook.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wxy on 2020/3/27.
 */
public class VideoPlayDialog extends Dialog {
    Context mContext;
    StandardGSYVideoPlayer videoPlayer;
    List<String> imgUrl;
    private OrientationUtils orientationUtils;
    private RecyclerView rcTitle;
    private int prePos;
    private TitleListAdapter mAdapter;

    public VideoPlayDialog(@NonNull Context context, List<String> imaUrl) {
        super(context, R.style.BaseDialog);
        this.mContext = context;
        this.imgUrl = imaUrl;
        this.setCanceledOnTouchOutside(true);
        this.show();
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        int deviceWidth = DeviceUtils.deviceWidth(getContext());
        int margin = DeviceUtils.dp2px(getContext(), 30);
        lp.width = deviceWidth - margin;
        lp.height = lp.width * 3 / 2;
        dialogWindow.setAttributes(lp);
    }

    protected VideoPlayDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_video_play, null);
        videoPlayer = view.findViewById(R.id.iv_img);
        rcTitle = view.findViewById(R.id.rc);

        List<TitleBean> list = new ArrayList<>();
        for (int i = 0; i < imgUrl.size(); i++) {
            if (i==0) list.add(new TitleBean("视频" + (i + 1),true));
            else
            list.add(new TitleBean("视频" + (i + 1),false));
        }

        rcTitle.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        mAdapter = new TitleListAdapter(list);
        rcTitle.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                if (prePos==position) return;
                TitleBean item = mAdapter.getItem(prePos);
                item.setSelected(false);
                TitleBean item1 = mAdapter.getItem(position);
                item1.setSelected(true);
                mAdapter.notifyDataSetChanged();
                prePos = position;

                videoPlayer.release();
                videoPlayer.setUp(imgUrl.get(position), true, list.get(position).getName());
                videoPlayer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        videoPlayer.startPlayLogic();
                    }
                }, 1000);
            }
        });
        videoPlayer.setUp(imgUrl.get(0), true, list.get(0).getName());

        //增加封面
//        ImageView imageView = new ImageView(mContext);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);
//        videoPlayer.setThumbImageView(imageView);

        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);

        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);

        //设置旋转
//        orientationUtils = new OrientationUtils(Util.getActivityFromCtx(mContext), videoPlayer);

        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setVisibility(View.GONE);

        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);

        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //过渡动画
//        initTransition();
        setContentView(view);
    }

    private void initTransition() {
        videoPlayer.startPlayLogic();
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
    }

}
