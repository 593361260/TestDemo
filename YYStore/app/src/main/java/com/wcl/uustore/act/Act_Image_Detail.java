package com.wcl.uustore.act;

import android.net.Uri;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wcl.uustore.R;
import com.wcl.uustore.baseAct.BaseAct;
import com.wcl.uustore.model.RemarkImageBean;
import com.wcl.uustore.tool.AppTools;
import com.wcl.uustore.widget.viewpage.JazzyViewPager;
import com.wcl.uustore.widget.viewpage.OutlineContainer;

import java.util.ArrayList;

/**
 * Created by DoctorY on 2016/4/22.
 * 应用详情图片
 */
public class Act_Image_Detail extends BaseAct {

    private JazzyViewPager mJazzyViewPager;
    private LinearLayout mLinea_container;
    private View mMove_point;
    private ArrayList<RemarkImageBean> mData;
    private int mCurrent;
    private int mPointSpace;

    @Override
    protected void getMessage(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_iamge_detail;
    }

    @Override
    protected void initView() {
        mJazzyViewPager = (JazzyViewPager) findViewById(R.id.jazzyViewpager);
        mLinea_container = (LinearLayout) findViewById(R.id.gudie_point_container);
        mMove_point = findViewById(R.id.view_move_point);
        mJazzyViewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Stack);
        mMove_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // 获得两个点间的距离
                mPointSpace = mLinea_container.getChildAt(1).getLeft() - mLinea_container.getChildAt(0).getLeft();
                mMove_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    protected void initData() {
        mData = getIntent().getParcelableArrayListExtra("imageList");
        if (null == mData) {
            /**
             * 数据为空  界面停止
             */
            return;
        }
        mCurrent = getIntent().getIntExtra("currentImg", 0);
        createPoint(mData.size());
        mJazzyViewPager.setCurrentItem(mCurrent);
        MyPagerAdapter adapter = new MyPagerAdapter(mData);
        mJazzyViewPager.setAdapter(adapter);
        mJazzyViewPager.setCurrentItem(mCurrent);
        mJazzyViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int left = (int) (mPointSpace * positionOffset + 0.5f);

                // 2. 改变选中点的marginleft
                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mMove_point.getLayoutParams();
                params.leftMargin = left + mPointSpace * position;

                mMove_point.setLayoutParams(params);
                Log.d("name", "");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("name", "");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("name","");
            }
        });

    }

    /**
     * 动态创建滑动的小点
     *
     * @param position
     */
    private void createPoint(int position) {
        for (int a = 0; a < position; a++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.point_normal);
            int space = AppTools.dip2px(this, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(space, space);
            if (a != 0) {
                params.leftMargin = AppTools.dip2px(this, 10);
            }
            mLinea_container.addView(view, params);
        }
    }


    @Override
    protected void request(int flag) {

    }

    private class MyPagerAdapter extends PagerAdapter {
        private ArrayList<RemarkImageBean> data;
        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        public MyPagerAdapter(ArrayList<RemarkImageBean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            if (null != data)
                return data.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == object;
            } else {
                return view == object;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            SimpleDraweeView draweeView = new SimpleDraweeView(Act_Image_Detail.this);
            draweeView.setLayoutParams(mLayoutParams);
            DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(Uri.parse(data.get(position).getImageUrl()))
                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                            .build();
            draweeView.setController(draweeController);
            container.addView(draweeView);
            mJazzyViewPager.setObjectForPosition(draweeView, position);
            return draweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mJazzyViewPager.findViewFromObject(position));
        }
    }
}
