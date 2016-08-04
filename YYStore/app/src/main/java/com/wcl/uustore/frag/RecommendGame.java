package com.wcl.uustore.frag;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wcl.uustore.BaseFrag.BaseFrag;
import com.wcl.uustore.R;
import com.wcl.uustore.act.Act_Detail;
import com.wcl.uustore.act.Act_VedioPlay;
import com.wcl.uustore.adapter.BaseAdapterHelper;
import com.wcl.uustore.adapter.BaseQuickAdapter;
import com.wcl.uustore.adapter.QuickAdapter;
import com.wcl.uustore.model.ApkDetailInfoBean;
import com.wcl.uustore.model.SeminarInfoBean;
import com.wcl.uustore.paser.ApkDetailXmlParser;
import com.wcl.uustore.paser.SeminarListXmlParser;
import com.wcl.uustore.tool.AppConfig;
import com.wcl.uustore.tool.AppTools;
import com.wcl.uustore.tool.ConstantString;
import com.wcl.uustore.tool.PhoneUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DoctorY on 2016/4/8.
 * <p/>
 * 视频播放的连接 http://nc-gametrailer.wdjcdn.com/newtrailer/1441977634844wandoujia.mp4
 */
public class RecommendGame extends BaseFrag implements View.OnClickListener {

    final String TAG = "RecommendGame";
    /**
     * U荐三甲请求
     */
    private final int U_RECOMMEND_TYPE = 0;
    private final int WEEK_HOT_TYPE = 1;
    private final int FEATURE_SPECIAL__TYPE = 2;

    private List<ApkDetailInfoBean> uRecommData = new ArrayList<>();
    private List<ApkDetailInfoBean> otherData = new ArrayList<>();
    private List<SeminarInfoBean> mSeminData = new ArrayList<>();
    String s1 = "http://apps.shua.cn/online/getTodayApp.go?screentype=480&screensize=1776x1080";
    String s4 = "http://apps.shua.cn/online/seminarList.go?seminartype=soft&page=0&pageSize=200&deviceId=358240058610346&time=1460628834660&screentype=480&screensize=1776x1080";
    String detail = "http://apps.shua.cn/online/appinfo.go?appId=317322&deviceId=358240058610346&time=1460683124138&screentype=480&screensize=1776x1080";

    private ViewPager mViewPager;
    private PhoneUtil mPhoneUtil;
    private RecyclerView mRecyclerView_week_hot;
    private RecyclerView mRecyclerView_game_hot;
    private RecyclerView mRecyclerView_special;
    private RecyclerView mRecyclerView_comment_best;
    private QuickAdapter mQuickAdapter;
    private TextView mRecomm_tv_qianri;
    private TextView mRecomm_tv_tommrow;
    private TextView mRecomm_tv_toady;
    private QuickAdapter mSubAdapter;
    private Button mBtn_play_vedio;

    @Override
    protected void getMessage(Message msg) {
        dismissDialog();
        switch (msg.what) {
            case U_RECOMMEND_TYPE://U荐三甲
                ApkDetailXmlParser parser = new ApkDetailXmlParser(getActivity());
                List<ApkDetailInfoBean> detailData = parser.parse((String) msg.obj);
//                for (ApkDetailInfoBean apk : detailData)
//                    Log.d("name", apk.toString());
                uRecommData.addAll(detailData);
                U_RecommAdapter U_Adapter = new U_RecommAdapter();
                mViewPager.setAdapter(U_Adapter);
                break;
            case WEEK_HOT_TYPE://小item推荐数据
                String da = (String) msg.obj;
                ApkDetailXmlParser parser1 = new ApkDetailXmlParser(getActivity());
                List<ApkDetailInfoBean> detailData1 = parser1.parse((String) msg.obj);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerView_week_hot.setLayoutManager(manager);
                mRecyclerView_game_hot.setLayoutManager(new GridLayoutManager(getActivity(), detailData1.size()));
//                mRecyclerView_special.setLayoutManager(new GridLayoutManager(getActivity(), detailData1.size()));
                mRecyclerView_comment_best.setLayoutManager(new GridLayoutManager(getActivity(), detailData1.size()));
                otherData.addAll(detailData1);
                mRecyclerView_week_hot.setAdapter(mQuickAdapter);
                mRecyclerView_game_hot.setAdapter(mQuickAdapter);
//                mRecyclerView_special.setAdapter(mQuickAdapter);
//                mRecyclerView_comment_best.setAdapter(mQuickAdapter);
//                mQuickAdapter.notifyDataSetChanged();
                break;
            case FEATURE_SPECIAL__TYPE:
                SeminarListXmlParser xmlParser = new SeminarListXmlParser(getActivity());
                List<SeminarInfoBean> seminBean = xmlParser.parse((String) msg.obj);
                mSeminData.addAll(seminBean);
                LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
                manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerView_special.setLayoutManager(manager1);
                mRecyclerView_special.setAdapter(mSubAdapter);
                mSubAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_recommend;
    }

    @Override
    protected void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mRecyclerView_week_hot = (RecyclerView) view.findViewById(R.id.recyclerview_week_hot);
        mRecyclerView_game_hot = (RecyclerView) view.findViewById(R.id.recyclerview_game_hot);
        mRecyclerView_special = (RecyclerView) view.findViewById(R.id.recyclerview_special);
        mRecyclerView_comment_best = (RecyclerView) view.findViewById(R.id.recyclerview_comment_best);

        mRecomm_tv_qianri = (TextView) view.findViewById(R.id.recommend_tv_qianri);
        mRecomm_tv_tommrow = (TextView) view.findViewById(R.id.recommend_tv_tommorow);
        mRecomm_tv_toady = (TextView) view.findViewById(R.id.recommend_tv_Toady);
        mBtn_play_vedio = (Button) view.findViewById(R.id.btn_play_Vedio);
        mViewPager.setCurrentItem(0);
        /**
         * 解决 嵌套滚动适配问题
         */
        mRecyclerView_week_hot.setNestedScrollingEnabled(false);
        mRecyclerView_game_hot.setNestedScrollingEnabled(false);
//        mRecyclerView_special.setNestedScrollingEnabled(false);
        mRecyclerView_comment_best.setNestedScrollingEnabled(false);
        mBtn_play_vedio.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPhoneUtil = PhoneUtil.getInstance(getActivity());
        /**
         * 橱窗滚动小item的适配器
         */
        mQuickAdapter = new QuickAdapter(getActivity(), R.layout.item_recommend_scroll, otherData) {
//            RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(AppTools.dip2px(getActivity(), 64), AppTools.dip2px(getActivity(), 80));

            @Override
            protected void convert(BaseAdapterHelper helper, Object item) {
                ApkDetailInfoBean bean = (ApkDetailInfoBean) item;
                SimpleDraweeView draweeView = helper.getDraweeView(R.id.ItemImage);
                Uri uri = Uri.parse(bean.getIconUrl());
//                draweeView.setLayoutParams(mParams);
                draweeView.setImageURI(uri);
                helper.getTextView(R.id.ItemText).setText(bean.getAppName());
            }

        };
        mQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ApkDetailInfoBean bean = otherData.get(position);
                Intent intent = new Intent(getActivity(), Act_Detail.class);
                intent.putExtra("apkId", bean.getId() + "");
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        mSubAdapter = new QuickAdapter(getActivity(), R.layout.item_recommend_sub, mSeminData) {
            float width = AppTools.getWindowsWidth(getActivity());
            int height = (int) (width * 1.0f / 2 * 330 / 1008.0f + 0.5f);

            RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams((int) (width * 1.0f / 2 + 0.5f) - 30,
                    (int) (height * 1.5f));

            @Override
            protected void convert(BaseAdapterHelper helper, Object item) {
//                Log.d(TAG, "width  :" + width + "  hegith  " + height);
                SeminarInfoBean bean = (SeminarInfoBean) item;
                SimpleDraweeView draweeView = helper.getDraweeView(R.id.ItemImage);

                DraweeController draweeController =
                        Fresco.newDraweeControllerBuilder()
                                .setUri(Uri.parse(bean.getIconUrl()))
                                .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                                .build();
                draweeView.setController(draweeController);
//                draweeView.setImageURI(Uri.parse(bean.getIconUrl()));
                draweeView.setLayoutParams(mParams);
            }

        };


        mSubAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {



            }
        });

        final int color = mRecomm_tv_qianri.getCurrentTextColor();
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d(TAG, "position   " + position + "    " +
//                        "  " + positionOffset + "  positionOffsetPixels  " + positionOffsetPixels);
                /**
                 * 更换当前选中的状态
                 */
                /*if (position == 0) {//0-->1
//                    mRecomm_tv_qianri.setTextColor(AppTools.calculateStatusColor(color, (int) (100.0f * (1 - positionOffset) + 0.5f)));
                    mRecomm_tv_tommrow.setTextColor(AppTools.calculateStatusColor(color, (int) (100.0f * positionOffset + 0.5f)));
                    mRecomm_tv_qianri.setTextColor(AppTools.calculateStatusColor(color, getResources().getColor(R.color.status_blue), positionOffset));
//                    mRecomm_tv_tommrow.setTextColor(AppTools.calculateStatusColor(getResources().getColor(R.color.control_activated_green),color, positionOffset));
                } else if (position == 1) {//1-->2  1-->0
//                    mRecomm_tv_tommrow.setTextColor(AppTools.calculateStatusColor(color, (int) (100.0f * (1 - positionOffset) + 0.5f)));
//                    mRecomm_tv_toady.setTextColor(AppTools.calculateStatusColor(color, (int) (100.0f * positionOffset + 0.5f)));

                    mRecomm_tv_toady.setTextColor(AppTools.calculateStatusColor(color, getResources().getColor(R.color.status_blue), positionOffset));
                    mRecomm_tv_tommrow.setTextColor(AppTools.calculateStatusColor(color, getResources().getColor(R.color.control_activated_green), positionOffset));

                }*/
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        Log.d(TAG, "dip2px : " + AppTools.dip2px(getActivity(), 60) + "  px2dip " + AppTools.px2dip(getActivity(), 60));
        request(U_RECOMMEND_TYPE);
        request(WEEK_HOT_TYPE);
        request(FEATURE_SPECIAL__TYPE);
    }

    @Override
    protected void request(int flag) {
//        showLoading();
        switch (flag) {
            case U_RECOMMEND_TYPE:
                httpHelper.NetObject(Request.Method.GET, s1, null, String.class, new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        sendMsg(U_RECOMMEND_TYPE, o);
                    }
                });
                break;
            case WEEK_HOT_TYPE:
                String sss = ConstantString.BASEURL + "online/getRemd.go?type=soft&page=0&deviceId=" + mPhoneUtil.getIMEI() +
                        "&time=" + AppConfig.currenttime + "&screentype=" + AppConfig.SCREENTYPE + "&screensize=" + AppConfig.SCREENSIZE;
                httpHelper.NetObject(Request.Method.GET, sss, null, String.class, new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        sendMsg(WEEK_HOT_TYPE, o);
                    }
                });
                break;
            case FEATURE_SPECIAL__TYPE:
                String s2 = ConstantString.BASEURL + "online/seminarList.go?seminartype=soft&page=0&pageSize=200&deviceId=" + mPhoneUtil.getIMEI() +
                        "&time=" + AppConfig.currenttime + "&screentype=" + AppConfig.SCREENTYPE + "&screensize=" + AppConfig.SCREENSIZE;
                httpHelper.NetObject(Request.Method.GET, s2, null, String.class, new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        sendMsg(FEATURE_SPECIAL__TYPE, o);
                    }
                });

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_Vedio:
                startActivity(new Intent(getActivity(), Act_VedioPlay.class));
                break;

            default:
                break;
        }
    }


    /**
     * U 荐三甲
     */

    private class U_RecommAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return uRecommData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SimpleDraweeView view = new SimpleDraweeView(getActivity());
            Uri uri = Uri.parse(uRecommData.get(position).getAboutImgUrl());
            view.setImageURI(uri);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
