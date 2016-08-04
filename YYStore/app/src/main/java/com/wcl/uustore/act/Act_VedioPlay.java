package com.wcl.uustore.act;

import android.os.Message;
import android.widget.RelativeLayout;

import com.wcl.uustore.R;
import com.wcl.uustore.baseAct.BaseAct;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by DoctorY on 2016/4/27.
 * <p/>
 * 推荐游戏视频播放
 */

public class Act_VedioPlay extends BaseAct {

    private String mp4Url = "http://movie.ks.js.cn/flv/other/1_0.mp4";
    private VideoView mVideoView;
    private RelativeLayout mRelative_bg;

    @Override
    protected void getMessage(Message msg) {

    }

    @Override
    public int getLayoutId() {
//        Vitamio.initialize(this);
        return R.layout.act_vedio_play;
    }

    @Override
    protected void initView() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mRelative_bg = (RelativeLayout) findViewById(R.id.rl_bg);

    }

    @Override
    protected void initData() {
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        mVideoView.requestFocus();
        mVideoView.setVideoPath(mp4Url);
        mVideoView.setMediaController(new MediaController(this)); //设置媒体控制器
//        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);   //设置视频的缩放参数,这里设置为拉伸


        /*mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });*/
    }

    @Override
    protected void request(int flag) {

    }
}
