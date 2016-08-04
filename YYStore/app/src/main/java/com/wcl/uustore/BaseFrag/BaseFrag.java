package com.wcl.uustore.BaseFrag;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wcl.uustore.R;
import com.wcl.uustore.http.HttpHelper;

import java.lang.reflect.Field;

/**
 * Created by DoctorY on 2016/4/9.
 */
public abstract class BaseFrag extends Fragment {

    private String tag = "BaseFrag";

    private AlertDialog mDialog;
    private AlertDialog.Builder mBuilder;
    //volley请求队列
    protected RequestQueue requestQueue;
    protected HttpHelper httpHelper;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    private View mView;
    private Toast mToast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (null != parent) {
                parent.removeView(mView);
            }
        } else {
            mView = inflater.inflate(getLayoutId(), null);
            requestQueue = Volley.newRequestQueue(getActivity());
            httpHelper = new HttpHelper(getActivity());
            initView(mView);
            initData();
        }
        return mView;
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getMessage(msg);
        }
    };

    /**
     * 简化handler消息发送
     *
     * @param handler
     * @param msgWhat 消息类型
     * @param msgObj  消息数据
     */
    protected void sendMsg(android.os.Handler handler, int msgWhat, Object msgObj) {
        Message msg = handler.obtainMessage();
        msg.obj = msgObj;
        msg.what = msgWhat;
        handler.sendMessage(msg);
    }

    protected void sendMsg(int msgWhat, Object msgObj) {
        Message msg = handler.obtainMessage();
        msg.obj = msgObj;
        msg.what = msgWhat;
        handler.sendMessage(msg);
    }

    /***
     * Handler回调方法
     */
    protected abstract void getMessage(Message msg);

    /**
     * 与activity UI交互
     * 或
     * 同一activity中的其他fragment UI交互时，重写此方法
     * simple: Button btn=getActivity().findViewById(R.id.btn);
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    /**
     * 布局ID
     *
     * @return layoutID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化布局
     */
    protected abstract void initView(View view);

    /**
     * 初始化数据
     */
    protected abstract void initData();


    protected abstract void request(int flag);

    /**
     * Toast提醒
     * <p/>
     * 防止多次弹出
     */
    protected void showToast(String text) {
        if (null == mToast)
            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        else
            mToast.setText(text);
        mToast.show();
    }

    /***
     * 当解除与activity关联时
     * 解决fragment嵌套fragment出现的问题：no activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            //参数是固定写法
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 判断当前fg是否可见，实现懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();

    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 显示正在加载
     */
    protected void showLoading() {
        if (null == mBuilder && mDialog == null) {
            mBuilder = new AlertDialog.Builder(getActivity());
            View view = View.inflate(getActivity(), R.layout.dialog_loadbar, null);
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
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mToast.cancel();
    }
}
