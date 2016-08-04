package com.wcl.uustore.act;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.wcl.uustore.R;
import com.wcl.uustore.adapter.BaseAdapterHelper;
import com.wcl.uustore.adapter.BaseQuickAdapter;
import com.wcl.uustore.adapter.QuickAdapter;
import com.wcl.uustore.baseAct.BaseAct;
import com.wcl.uustore.model.ApkDetailInfoBean;
import com.wcl.uustore.model.OtherApkLinkBean;
import com.wcl.uustore.model.RemarkImageBean;
import com.wcl.uustore.paser.ApkDetailInfoXmlParser;
import com.wcl.uustore.tool.AppConfig;
import com.wcl.uustore.tool.AppTools;
import com.wcl.uustore.tool.ConstantInt;
import com.wcl.uustore.tool.ConstantString;
import com.wcl.uustore.tool.PhoneUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DoctorY on 2016/4/15.
 * 详情展示界面
 */
public class Act_Detail extends BaseAct {
    private String temp = "http://apps.shua.cn/online/appinfo.go?appId=238520&deviceId=358240058610346&time=1461047860035&screentype=480&screensize=1776x1080";
    private String tag = "Act_Detail";

    private CoordinatorLayout mCoorLayout;
    private NestedScrollView mScrollView;
    private BottomSheetBehavior mBehavior;
    private View mTop_view;
    private String mAppId;
    private PhoneUtil mPhoneUtil;
    private ApkDetailInfoBean mApkInfo;
    private List<OtherApkLinkBean> mOtherBean = new ArrayList<>();
    private List<RemarkImageBean> mRemarkBean = new ArrayList<>();
    private SimpleDraweeView mTop_drawView_top;
    private TextView mItem_name_top;
    private TextView mItem_size_top;
    private TextView mItem_type_top;
    private TextView mItem_officaial_top;
    private TextView mItem_adv_top;
    private ProgressBar mItem_progress_top;
    private Button mItem_btn_downLoad_top;

    /**
     * 详情显示界面中的应用图标
     */
    private SimpleDraweeView mDraweeView_detail;
    /**
     * 详情显示界面中的应用名称
     */
    private TextView mItem_name_detail;
    /**
     * 详情显示界面中的应用大小
     */
    private TextView mItem_size_detail;
    /**
     * 详情显示界面中的应用类型
     */
    private TextView mItem_type_detail;
    /**
     * 详情显示界面中的应用官方类型
     */
    private TextView mItem_offical_detail;
    /**
     * 详情显示界面中的应用开发公司
     */
    private TextView mItem_adv_detail;
    /**
     * 详情显示界面中的应用图片介绍
     */
    private HorizontalScrollView mHorizontalScrollView;
    /**
     * 详情显示界面装载图片的容器
     */
    private LinearLayout mItem_image_container;
    /**
     * 详情显示界面中的应用介绍
     */
    private TextView mItem_tvSummary_detail;
    /**
     * 详情显示界面中的应用详情
     */
    private TextView mItem_tvSummaryText_detail;
    /**
     * 详情显示界面中的应用了解更多
     */
    private TextView mItem_konw_detail;
    /**
     * 详情显示界面中的应用 新版本介绍
     */
    private TextView mItem_updateText_detail;
    /**
     * 详情显示界面中的应用 更新日期
     */
    private TextView mItem_updateTime_detail;
    /**
     * 详情显示界面中的应用  版本信息
     */
    private TextView mItem_versionCodeName_detail;
    /**
     * 详情显示界面中的应用 权限详情列表
     */
    private TextView mItem_permission_detail;
    /**
     * 详情显示界面中的应用 语言环境
     */
    private TextView mItem_languageName_detail;
    /**
     * 详情显示界面中的应用 android 版本
     */
    private TextView mItem_versionName_detail;
    /**
     * 详情显示界面中的应用相似应用
     */
    private RecyclerView mRecyclerView_detail;
    private QuickAdapter mAdapter;


    @Override
    protected void getMessage(Message msg) {
        switch (msg.what) {
            case ConstantInt.REQUEST_APP_INFO:
                dismissDialog();
                ApkDetailInfoXmlParser xmlParser = new ApkDetailInfoXmlParser(Act_Detail.this);
                HashMap<String, Object> apkInfo = xmlParser.parse((String) msg.obj);
                mApkInfo = (ApkDetailInfoBean) apkInfo.get("app");
                /**
                 * setting Top InVisiable View(Apk infos)
                 */
                mTop_drawView_top.setImageURI(Uri.parse(mApkInfo.getIconUrl()));
                mItem_name_top.setText(mApkInfo.getAppName());
                mItem_size_top.setText(Formatter.formatFileSize(Act_Detail.this, mApkInfo.getSize()));
                mItem_type_top.setText(mApkInfo.getType());
                mItem_officaial_top.setText(mApkInfo.getIsOffical());
                mItem_adv_top.setText(mApkInfo.getDeveloper());

                /**
                 * Setting Detail Apk info
                 */
                Spanned spanned = null;
                spanned = Html.fromHtml(mApkInfo.getRemark() == null || "".equals(mApkInfo.getRemark()) ? "暂无介绍" : mApkInfo.getRemark().trim());
                mDraweeView_detail.setImageURI(Uri.parse(mApkInfo.getIconUrl()));
                mItem_name_detail.setText(mApkInfo.getAppName());
                mItem_size_detail.setText(Formatter.formatFileSize(Act_Detail.this, mApkInfo.getSize()));
                mItem_type_detail.setText(mApkInfo.getType());
                mItem_offical_detail.setText(mApkInfo.getIsOffical());
                mItem_adv_detail.setText(mApkInfo.getDeveloper());
                mItem_tvSummary_detail.setText(mApkInfo.getAdremark());
                mItem_tvSummaryText_detail.setText(spanned);
                mItem_updateText_detail.setText(mApkInfo.getVersionInfo());
                mItem_updateTime_detail.setText(mApkInfo.getVersionTime());
                mItem_versionCodeName_detail.setText(mApkInfo.getVersionName());
                mItem_languageName_detail.setText(mApkInfo.getLanguage());
                mItem_versionName_detail.setText(AppTools.getAndroidVersion(mApkInfo.getMinSdkVersion()));

                /**
                 *
                 */

                mOtherBean.addAll((List<OtherApkLinkBean>) apkInfo.get("otherapks"));
                mAdapter.notifyDataSetChanged();
                ArrayList<RemarkImageBean> rmkImageList1 = null;
                rmkImageList1 = (ArrayList<RemarkImageBean>) apkInfo.get("remarkimages_h");
                if (rmkImageList1 == null) {
                    rmkImageList1 = (ArrayList<RemarkImageBean>) apkInfo.get("remarkimages_m");
                }
                if (rmkImageList1 == null) {
                    rmkImageList1 = (ArrayList<RemarkImageBean>) apkInfo.get("remarkimages_l");
                }
                if (null != rmkImageList1)//创建图片
//                    mRemarkBean.addAll(rmkImageList1);
                    ceratImgDetail(rmkImageList1);
                else//隐藏图片图片区域
                    mHorizontalScrollView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 创建应用的图片详情
     *
     * @param rmkImageList1
     */
    private void ceratImgDetail(final ArrayList<RemarkImageBean> rmkImageList1) {
        mItem_image_container.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AppTools.dip2px(this, 120), AppTools.dip2px(this, 200));

        for (int a = 0; a < rmkImageList1.size(); a++) {
            if (a != 0)
                params.leftMargin = AppTools.dip2px(this, 8);
            SimpleDraweeView draweeView = new SimpleDraweeView(this);
//            draweeView.setImageURI(Uri.parse(/*rmkImageList1.get(a).getImageUrl()*/"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2601415697,1262435462&fm=116&gp=0.jpg"));
            mItem_image_container.addView(draweeView, params);
            DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(Uri.parse(rmkImageList1.get(a).getImageUrl()))
                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                            .build();

            draweeView.setController(draweeController);
            /**
             * Click View Expend
             */
            final int finalA = a;
            draweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Act_Detail.this, Act_Image_Detail.class);
                    intent.putExtra("imageList", rmkImageList1);
                    intent.putExtra("currentImg", finalA);
                    startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getLayoutId() {
        /**
         * 设置背景色
         */
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
//        getWindow().getDecorView().setBackgroundDrawable(null);
        return R.layout.act_detail;
    }

    @Override
    protected void initView() {
        mCoorLayout = (CoordinatorLayout) findViewById(R.id.cl);
        mScrollView = (NestedScrollView) findViewById(R.id.bottom_sheet);
        /**
         * 头部隐藏的View
         */
        mTop_view = findViewById(R.id.view_top);
        mTop_view.setVisibility(View.GONE);
//        ObjectAnimator.ofFloat(mTop_view, "translationY",0).setDuration(1).start();
        mTop_view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mTop_view.getViewTreeObserver().removeOnPreDrawListener(this);
                mTop_view.setVisibility(View.VISIBLE);
                ObjectAnimator animator = ObjectAnimator.ofFloat(mTop_view, "translationY", -mTop_view.getHeight() - AppTools.getStatusBarHeight(Act_Detail.this));
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(0).start();
                return true;
            }
        });

        mTop_drawView_top = (SimpleDraweeView) findViewById(R.id.new_detail_item_ivItem_Top);
        mItem_name_top = (TextView) findViewById(R.id.new_detail_item_name_top);
        mItem_size_top = (TextView) findViewById(R.id.new_detail_item_size_Top);
        mItem_type_top = (TextView) findViewById(R.id.new_detail_item_typeImage_Top);
        mItem_officaial_top = (TextView) findViewById(R.id.new_detail_item_official_Top);
        mItem_adv_top = (TextView) findViewById(R.id.new_detail_item_adv_Top);
        mItem_progress_top = (ProgressBar) findViewById(R.id.new_detail_item_apkProgressBar_Top);
        mItem_btn_downLoad_top = (Button) findViewById(R.id.new_detail_item_btnDownLoad_Top);

        /**
         * 整个显示详情
         */
        mDraweeView_detail = (SimpleDraweeView) findViewById(R.id.new_detail_item_ivItem);
        mItem_name_detail = (TextView) findViewById(R.id.new_detail_item_name);
        mItem_size_detail = (TextView) findViewById(R.id.new_detail_item_size);
        mItem_type_detail = (TextView) findViewById(R.id.new_detail_item_typeImage);
        mItem_offical_detail = (TextView) findViewById(R.id.new_detail_item_official);
        mItem_adv_detail = (TextView) findViewById(R.id.new_detail_item_adv);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.scrollView_container);
        mItem_image_container = (LinearLayout) findViewById(R.id.linear_container);
        mItem_tvSummary_detail = (TextView) findViewById(R.id.new_detail_item_tvSummaryTitle);
        mItem_tvSummaryText_detail = (TextView) findViewById(R.id.new_detail_item_tvSummaryText);
        mItem_konw_detail = (TextView) findViewById(R.id.new_detail_item_know);
        mItem_updateText_detail = (TextView) findViewById(R.id.new_detail_item_tvVersionUpdateText);
        mItem_updateTime_detail = (TextView) findViewById(R.id.new_detail_item_tvUpdateName);
        mItem_versionCodeName_detail = (TextView) findViewById(R.id.new_detail_item_tvVersionCodeName);
        mItem_permission_detail = (TextView) findViewById(R.id.new_detail_item_appPermission);
        mItem_languageName_detail = (TextView) findViewById(R.id.new_detail_item_tvLanguageName);
        mItem_versionName_detail = (TextView) findViewById(R.id.new_detail_item_tvVersionName);
//        findViewById(R.id.new_detail_item_tvDeveloperName);
        mRecyclerView_detail = (RecyclerView) findViewById(R.id.recyclerview_recommend_detail);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView_detail.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        mPhoneUtil = PhoneUtil.getInstance(Act_Detail.this);
        mAppId = getIntent().getStringExtra("apkId");
        request(ConstantInt.REQUEST_APP_INFO);
        mBehavior = BottomSheetBehavior.from(mScrollView);
        mBehavior.setPeekHeight(AppTools.getWindowsHeight(this) - 360);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            private boolean isOpen = false;
            private boolean isClose = false;

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                /**
                 * state 状态
                 * 1:页面在底部被拖动
                 * 2:向下降
                 * 3:表被扩大
                 * 4: 被折叠
                 * 5:表被隐藏
                 */

                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    finish();
                }
                //这里是bottomSheet 状态的改变，根据slideOffset可以做一些动画
//                Log.d(tag, "状态的改变" + newState);
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (isOpen) {
                        return;
                    }
//                    mTop_view.setVisibility(View.VISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mTop_view, "translationY", 0);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.setDuration(200).start();
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isOpen = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isOpen = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                } else {
                    if (isClose)
                        return;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mTop_view, "translationY", -mTop_view.getHeight() - AppTools.getStatusBarHeight(Act_Detail.this));
                    animator.setInterpolator(new LinearInterpolator());
                    animator.setDuration(200).start();
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isClose = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isClose = false;
//                            mTop_view.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
//                Log.d(tag, "拖动的回调" + slideOffset/* * AppTools.getWindowsHeight(Act_Detail.this)*/);
                if (slideOffset < 0 && slideOffset > -0.9) {
                    try {
                        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + (int) (99 * (slideOffset + 1)) + "000000")));
                    } catch (Exception e) {
//                        Log.d(tag, "颜色值" + "#" + (int) (99 * (slideOffset + 1)) + "000000");
                    }
                } else if (slideOffset == -1) {
                    getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                }
            }
        });

        mAdapter = new QuickAdapter(this, R.layout.item_recommend_scroll, mOtherBean) {
            @Override
            protected void convert(BaseAdapterHelper helper, Object item) {
                OtherApkLinkBean bean = (OtherApkLinkBean) item;
                SimpleDraweeView draweeView = helper.getDraweeView(R.id.ItemImage);
                Uri uri = Uri.parse(bean.getIconUrl());
                draweeView.setImageURI(uri);
                helper.getTextView(R.id.ItemText).setText(bean.getApkName());
            }

        };
        mRecyclerView_detail.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO  item 的点击事件


            }
        });
    }

    @Override
    protected void request(int flag) {
        switch (flag) {
            case ConstantInt.REQUEST_APP_INFO:
                showLoading();
                String apkInfoUrl = ConstantString.BASEURL + "online/appinfo.go?appId=" + mAppId + "&deviceId=" + mPhoneUtil.getIMEI() +
                        "&time=" + AppConfig.currenttime + "&screentype=" + AppConfig.SCREENTYPE + "&screensize=" + AppConfig.SCREENSIZE;
                httpHelper.NetObject(Request.Method.GET, apkInfoUrl, null, String.class, new Response.Listener() {
                    @Override
                    public void onResponse(Object o) {
                        sendMsg(ConstantInt.REQUEST_APP_INFO, o);
                    }
                });
                break;
        }
    }
}
