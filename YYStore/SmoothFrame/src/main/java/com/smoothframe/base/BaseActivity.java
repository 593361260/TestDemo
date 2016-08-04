package com.smoothframe.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.smoothframe.http.HttpHelper;
import com.smoothframe.util.SharePreference;
import com.smoothframe.util.Tools;
import com.smoothframe.view.CustomProgressDialog;


public abstract class BaseActivity extends FragmentActivity implements OnClickListener {
//    private ProgressDialog mProgressDialog;
    private CustomProgressDialog mProgressDialog;
    private Context mContext;
    protected Bundle savedInstanceState;
    //volley请求队列
//    protected RequestQueue requestQueue;
    protected HttpHelper httpHelper;

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getMessage(msg);
        }
    };


    /***
     * Handler回调方法
     * */
    protected abstract void getMessage(Message msg);
    /**
     * 设置用户是否可以操作当前的页面
     * true为可以  false为不可以
     * 可用于某些重要数据加载时，限制用户操作，强制等待数据加载完成
     */
    protected boolean isTouchEvent = true;

    /**
     * 得到上下文
     */
    public Context getContext() {
        return mContext;
    }




    /**
     * 布局ID
     * @return  layoutID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tools.initImageLoader(this);
        Tools.initDisplayImageOptions();
        initSystemBar();
        this.savedInstanceState = savedInstanceState;
        mContext = this.getApplicationContext();
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
        mProgressDialog = new CustomProgressDialog(this);
        /**
         * volley网络框架的使用
         * 请求队列加进mRequestQueue
         * */
//        requestQueue = Volley.newRequestQueue(this);
        httpHelper = new HttpHelper(this);
        initView();
        initData();
    }
    /**
     * 配置文件操作
     */
    private SharePreference spUtil;
    /**
     * 获得配置文件操作对象
     * @param spName 配置文件的名称
     * */
    public SharePreference getSpUtil(String spName) {
        if(spUtil==null)
            spUtil = new SharePreference(this,spName);
        return spUtil;
    }
    /**
     * 透明状态栏，仅4.0以上可用
     * */
    private void initSystemBar() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    /**
     * 简化handler消息发送
     * @param handler
     * @param msgWhat 消息类型
     * @param msgObj 消息数据
     * */
    protected void sendMsg(android.os.Handler handler,int msgWhat,Object msgObj) {
        Message msg = handler.obtainMessage();
        msg.obj = msgObj;
        msg.what = msgWhat;
        handler.sendMessage(msg);
    }
    protected void sendMsg(int msgWhat,Object msgObj) {
        Message msg = handler.obtainMessage();
        msg.obj = msgObj;
        msg.what = msgWhat;
        handler.sendMessage(msg);
    }
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
    }
    /**
     * Toast提醒
     * */
    protected void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }



    /**
     * 网络请求统一放置该方法内
     */
    protected abstract void request(int requestFlag);

    /**
     * 显示/隐藏 等待框
     * */
    protected void showProgress(){
        mProgressDialog.show();
    }
    protected void dismissProgress(){
        mProgressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTouchEvent=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //暂停这个队列的请求
        httpHelper.stop();
//        requestQueue.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        httpHelper.start();
//        requestQueue.start();
    }

    @Override
    public void finish() {
        super.finish();
        //取消这个队列里的所有请求
        httpHelper.cancelAll();
//        requestQueue.cancelAll(this);
        mProgressDialog.dismiss();
    }
    /**
     * 拦截触摸事件
     * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isTouchEvent) {
            return super.dispatchTouchEvent(ev);
        } else {
            return false;
        }
    }
    /**
     * 设置progressdialog是否相应窗口取消
     * @cancel true为可以，false为不可以
     * */
    public void setProgressDialogCanceledOnTouchOutside(boolean cancel){
        mProgressDialog.setCanceledOnTouchOutside(cancel);
    }

}
