package com.smoothframe.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.sjkj.myapplication.R;


/**
 * 自定义等待框
 */
public class CustomProgressDialog extends ProgressDialog {
    private ImageView red;
    private ImageView green, blue;

    public CustomProgressDialog(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        red = (ImageView) findViewById(R.id.red);
        green = (ImageView) findViewById(R.id.green);
        blue = (ImageView) findViewById(R.id.blue);
        starLoading();
    }

    public void starLoading() {
        startAnimation1();
        startAnimation2();
        startAnimation3();
    }

    private void startAnimation1() {
        TranslateAnimation ta1 = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 5f,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF,
                0);
        ta1.setDuration(1000);
        ta1.setStartTime(0);
        ta1.setRepeatCount(Integer.MAX_VALUE);
        ta1.setRepeatMode(Animation.REVERSE);
        red.startAnimation(ta1);
    }

    private void startAnimation2() {
        ScaleAnimation sa = new ScaleAnimation(0.0f, 2f, 0.0f, 2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(1000);
        sa.setRepeatCount(Integer.MAX_VALUE);
        sa.setRepeatMode(Animation.REVERSE);
        green.startAnimation(sa);
    }

    private void startAnimation3() {
        TranslateAnimation ta3 = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -5f,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF,
                0);
        ta3.setDuration(1000);
        ta3.setRepeatCount(Integer.MAX_VALUE);//往返的次数
        ta3.setRepeatMode(Animation.REVERSE);//设置往返动作
        blue.startAnimation(ta3);
    }

}
