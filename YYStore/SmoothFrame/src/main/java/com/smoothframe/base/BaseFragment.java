package com.smoothframe.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.smoothframe.http.HttpHelper;
import com.smoothframe.util.SharePreference;

import java.lang.reflect.Field;

public abstract class BaseFragment extends Fragment {
    private ProgressDialog mProgressDialog;
    //volley请求队列
    protected RequestQueue requestQueue;
    protected HttpHelper httpHelper;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    private View mView;
    private static Toast mToast;

    /**
     * 获得配置文件操作对象
     */
    public SharePreference getSpUtil(String spName) {
        if (spUtil == null)
            spUtil = new SharePreference(getActivity(), spName);
        return spUtil;
    }

    /**
     * 配置文件操作
     */
    private SharePreference spUtil = null;

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
            mProgressDialog = new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
            mProgressDialog.setMessage("加载中...");
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

    /**
     * 显示/隐藏 等待框
     */
    protected void showProgress() {
        if (mProgressDialog != null)
            mProgressDialog.show();
    }

    protected void dismissProgress() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
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
     * 设置progressdialog是否相应窗口取消
     *
     * @cancel true为可以，false为不可以
     */
    public void setProgressDialogCanceledOnTouchOutside(boolean cancel) {
        mProgressDialog.setCanceledOnTouchOutside(cancel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mToast.cancel();
    }
}
