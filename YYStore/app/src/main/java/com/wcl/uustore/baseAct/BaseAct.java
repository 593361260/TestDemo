package com.wcl.uustore.baseAct;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.wcl.uustore.R;
import com.wcl.uustore.http.HttpHelper;

/**
 * Created by DoctorY on 2016/4/7.
 */
public abstract class BaseAct extends AppCompatActivity {

    private AlertDialog mDialog;
    private AlertDialog.Builder mBuilder;
    protected HttpHelper httpHelper;
    protected boolean isClickPath = true;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getMessage(msg);

        }
    };
    private Toast mToast;

    protected abstract void getMessage(Message msg);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(getLayoutId());
        initSystem();
        httpHelper = new HttpHelper(this);
        initView();
        initData();
    }

    /**
     * 透明状态栏
     */
    protected void initSystem() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    /*| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION*/);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                   /* | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
    }

    /**
     * @return layout id
     */
    public abstract int getLayoutId();

    /**
     *
     */
    protected abstract void initView();

    /**
     *
     */
    protected abstract void initData();

    /**
     * request  netdata
     *
     * @param flag
     */
    protected abstract void request(int flag);

    protected void showToast(String showText) {
        if (null == mToast)
            mToast = Toast.makeText(this, showText, Toast.LENGTH_SHORT);
        else
            mToast.setText(showText);
        mToast.show();
    }

    /**
     * 发送Hanlder 消息
     *
     * @param msgWhat
     * @param msgObj
     */
    protected void sendMsg(int msgWhat, Object msgObj) {
        Message msg = Message.obtain();
        msg.what = msgWhat;
        msg.obj = msgObj;
        mHandler.sendMessage(msg);
    }

    /**
     * 显示正在加载
     */
    protected void showLoading() {
        if (null == mBuilder) {
            mBuilder = new AlertDialog.Builder(this, R.style.dialog_transparent);
            View view = View.inflate(this, R.layout.dialog_loadbar, null);
            mBuilder.setView(view);
            mDialog = mBuilder.show();
        } else {
            if (mDialog.isShowing()) {
                return;
            }
            mDialog.show();
        }
    }

    /**
     * 关闭正在加载
     */
    protected void dismissDialog() {
        if (null != mBuilder) {
            mDialog.dismiss();
        }
    }

    /**
     * 称为处理触摸屏事件。你可以重写这个
     * <p/>
     * 所有的触摸屏事件之前，他们被派往
     * <p/>
     * 窗口。一定要调用此实现的触摸屏事件
     * <p/>
     * 应该处理的。
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isClickPath)
            return super.dispatchTouchEvent(ev);
        else
            return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        httpHelper.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isClickPath = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        httpHelper.stop();
    }

    @Override
    public void finish() {
        super.finish();
        httpHelper.cancelAll();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
