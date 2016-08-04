package com.wcl.uustore.frag;

import android.net.Uri;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wcl.uustore.BaseFrag.BaseFrag;
import com.wcl.uustore.R;
import com.wcl.uustore.act.Act_Main;
import com.wcl.uustore.adapter.BaseAdapterHelper;
import com.wcl.uustore.adapter.BaseQuickAdapter;
import com.wcl.uustore.adapter.QuickAdapter;
import com.wcl.uustore.model.SortData;

import java.util.List;

/**
 * Created by DoctorY on 2016/3/8.
 * <p/>
 * <p/>
 * http://api.ring.51app.cn/r/cat.do
 */
public class SortFrag extends BaseFrag implements View.OnClickListener {

    private RecyclerView mRecycler_launguage;
    private RecyclerView mRecycler_style;
    private RecyclerView mRecycler_theme;
    private NestedScrollView mScrollView;
    private SimpleDraweeView mDrawCall;
    private TextView mTv_call;
    private SimpleDraweeView mDrawMsg;
    private TextView mTv_msg;
    private SimpleDraweeView mDrawAlarm;
    private TextView mTv_alarm;
    private Act_Main mMain;
    private LinearLayout mLinearCall;
    private LinearLayout mLinearMsg;
    private LinearLayout mLinearAlarm;

    @Override
    protected void getMessage(Message msg) {
        switch (msg.what) {
            case 0:
                dismissDialog();
                mScrollView.setVisibility(View.VISIBLE);
                String s = (String) msg.obj;
                SortData sortData = JSONObject.parseObject(s, SortData.class);
//                SortData sortData = (SortData) msg.obj;
                final List<SortData.BodyBean> body = sortData.getBody();
                List<SortData.BodyBean.ObjectsBean> bjgBean = body.get(0).getObjects();
                createTopView(bjgBean);
                Log.d("name", "--");
                QuickAdapter adapterLanguage = new QuickAdapter(getActivity(), R.layout.item_sort, body.get(1).getObjects()) {

                    @Override
                    protected void convert(BaseAdapterHelper helper, Object item) {
                        Log.d("name", "+ +");
                        SortData.BodyBean.ObjectsBean objectsBean = (SortData.BodyBean.ObjectsBean) item;
                        helper.getTextView(R.id.tv_title).setText(objectsBean.getName());
                        helper.getDraweeView(R.id.simpleView).setImageURI(Uri.parse(objectsBean.getIconUrl()));
                    }
                };
                mRecycler_launguage.setAdapter(adapterLanguage);
                adapterLanguage.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });


                QuickAdapter adapterStyle = new QuickAdapter(getActivity(), R.layout.item_sort, body.get(2).getObjects()) {

                    @Override
                    protected void convert(BaseAdapterHelper helper, Object item) {
                        SortData.BodyBean.ObjectsBean objectsBean = (SortData.BodyBean.ObjectsBean) item;
                        helper.getTextView(R.id.tv_title).setText(objectsBean.getName());
                        helper.getDraweeView(R.id.simpleView).setImageURI(Uri.parse(objectsBean.getIconUrl()));
                    }
                };
                mRecycler_style.setAdapter(adapterStyle);

                adapterStyle.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }
                });

                QuickAdapter adapterTheme = new QuickAdapter(getActivity(), R.layout.item_sort, body.get(3).getObjects()) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, Object item) {
                        SortData.BodyBean.ObjectsBean objectsBean = (SortData.BodyBean.ObjectsBean) item;
                        helper.getTextView(R.id.tv_title).setText(objectsBean.getName());
                        helper.getDraweeView(R.id.simpleView).setImageURI(Uri.parse(objectsBean.getIconUrl()));
                    }
                };
                mRecycler_theme.setAdapter(adapterTheme);
                adapterTheme.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    }
                });

//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppTools.dip2px(getActivity(), 100));
//                mRecycler_launguage.setLayoutParams(params);
//
//                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppTools.dip2px(getActivity(), 300));
//                mRecycler_style.setLayoutParams(params1);
//
//                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppTools.dip2px(getActivity(), 200));
//                mRecycler_theme.setLayoutParams(params2);
                break;
        }
    }

    /**
     * 创建顶部的View
     *
     * @param bjgBean
     */
    private void createTopView(final List<SortData.BodyBean.ObjectsBean> bjgBean) {
        mDrawCall.setImageURI(Uri.parse(bjgBean.get(0).getIconUrl()));
        mTv_call.setText(bjgBean.get(0).getName());

        mDrawMsg.setImageURI(Uri.parse(bjgBean.get(1).getIconUrl()));
        mTv_msg.setText(bjgBean.get(1).getName());

        mDrawAlarm.setImageURI(Uri.parse(bjgBean.get(2).getIconUrl()));
        mTv_alarm.setText(bjgBean.get(2).getName());
        mLinearCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mLinearMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mLinearAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    protected void initView(View view) {
        mRecycler_launguage = (RecyclerView) view.findViewById(R.id.recycler_Laungauge);
        mRecycler_style = (RecyclerView) view.findViewById(R.id.recycler_Style);
        mRecycler_theme = (RecyclerView) view.findViewById(R.id.recycler_Theme);
        mScrollView = (NestedScrollView) view.findViewById(R.id.netScroll);
        /**
         * TOP
         */
        mDrawCall = (SimpleDraweeView) view.findViewById(R.id.draw_call);
        mTv_call = (TextView) view.findViewById(R.id.tv_Call);
        mDrawMsg = (SimpleDraweeView) view.findViewById(R.id.draw_msg);
        mTv_msg = (TextView) view.findViewById(R.id.tv_Msg);
        mDrawAlarm = (SimpleDraweeView) view.findViewById(R.id.draw_alarm);
        mTv_alarm = (TextView) view.findViewById(R.id.tv_alarm);

        mLinearCall = (LinearLayout) view.findViewById(R.id.linear_Call);
        mLinearMsg = (LinearLayout) view.findViewById(R.id.linear_Msg);
        mLinearAlarm = (LinearLayout) view.findViewById(R.id.linear_Alarm);

        mRecycler_launguage.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecycler_style.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecycler_theme.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecycler_launguage.setNestedScrollingEnabled(false);
        mRecycler_style.setNestedScrollingEnabled(false);
        mRecycler_theme.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        mMain = (Act_Main) getActivity();
        request(0);
    }

    @Override
    protected void request(int flag) {
        switch (flag) {
            case 0:
                httpHelper.NetObject(Request.Method.GET, "http://api.ring.51app.cn/r/cat.do", null, SortData.class, new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        sendMsg(0, o);
                        Log.d("name", "success");
                    }
                });

                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_sort;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}