package com.wlwoon.workbook.video;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wlwoon.base.BaseActivity;
import com.wlwoon.workbook.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoActivity extends BaseActivity {


    @BindView(R.id.spVideo)
    SmartPickVideo videoPlayer;
    @BindView(R.id.bt)
    Button mBt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {
        //借用了jjdxm_ijkplayer的URL
        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String name = "普通";
        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name, source1);

        String source2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
        String name2 = "清晰";
        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, source2);

        List<String> list = new ArrayList<>();
        list.add(source1);
        list.add(source2);
        initVideo(list);
    }

    private void initVideo(List<String> videoUrls) {
        List<SwitchVideoModel> list = new ArrayList<>();
        for (String s : videoUrls) {
            SwitchVideoModel switchVideoModel = new SwitchVideoModel("安全帽视频", s);
            list.add(switchVideoModel);
        }

        videoPlayer.setUp(list, true, "安全帽视频");
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置旋转
//        orientationUtils = new OrientationUtils(this, videoPlayer);

        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setVisibility(View.GONE);

        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        videoPlayer.getBackButton().setVisibility(View.GONE);

//        //设置返回按键功能
//        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        //过渡动画
//        initTransition();


    }

    private void initTransition() {
        videoPlayer.startPlayLogic();
    }

    List<String> mStringList = new ArrayList<>();

    @OnClick(R.id.bt)
    public void onClick() {
        mStringList.add("http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4");
        mStringList.add("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
        VideoPlayDialog dialog = new VideoPlayDialog(this,mStringList);
    }
}