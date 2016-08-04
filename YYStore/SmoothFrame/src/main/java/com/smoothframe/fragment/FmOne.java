package com.smoothframe.fragment;

import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sjkj.myapplication.R;
import com.smoothframe.base.BaseFragment;
import com.smoothframe.fragment.childFragment.FmOneOne;
import com.smoothframe.fragment.childFragment.FmOneTwo;
import com.smoothframe.util.Tools;
import com.smoothframe.adapter.AdpPager;

public class FmOne extends BaseFragment implements OnClickListener {
    private ViewPager mViewPage;
    private TextView mTvCollection, mTvMake;
    private LinearLayout mLayConllection, mLayMake;
    private ImageView mImgLine;
    private int mCurrentIndex;
    private AdpPager mAdpPager;
    private int mWindowsWidth = 0;

    @Override
    protected void getMessage(Message msg) {
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_one;
    }

    @Override
    protected void initView(View view) {
        mViewPage = (ViewPager) view.findViewById(R.id.vp);
        mTvCollection = (TextView) view.findViewById(R.id.my_collection_tv);
        mTvMake = (TextView) view.findViewById(R.id.my_make_tv);
        mImgLine = (ImageView) view.findViewById(R.id.line);
        mLayConllection = (LinearLayout) view.findViewById(R.id.my_collection_linearlayout);
        mLayMake = (LinearLayout) view.findViewById(R.id.my_make_linearlayout);
        mLayConllection.setOnClickListener(this);
        mLayMake.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        //初始化文字颜色
        mTvCollection.setTextColor(getResources().getColor(R.color.primary_orange));
        mTvMake.setTextColor(getResources().getColor(R.color.primary_text));
        //初始化ImgLine的宽度
        mWindowsWidth = (int) Tools.getWindowsWidth(getActivity());
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mImgLine
                .getLayoutParams();
        lp.width = mWindowsWidth / 3 + 50;
        mImgLine.setLayoutParams(lp);
        //初始化适配器
        mAdpPager = new AdpPager(getActivity());
        mAdpPager.addTab(FmOneOne.class, null);
        mAdpPager.addTab(FmOneTwo.class, null);
        mViewPage.setAdapter(mAdpPager);
        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //resetTextView();
                switch (position) {
                    case 0:
                        mTvCollection.setTextColor(getResources().getColor(R.color.primary_orange));
                        mTvMake.setTextColor(getResources().getColor(R.color.primary_text));
                        break;
                    case 1:
                        mTvMake.setTextColor(getResources().getColor(R.color.primary_orange));
                        mTvCollection.setTextColor(getResources().getColor(R.color.primary_text));
                        break;
                }
                mCurrentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mImgLine
                        .getLayoutParams();
                onScorllImgLine(arg0, arg1, lp);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
     * 设置mTabLineIv的左边距 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1;
     * 1->0
     */
    private void onScorllImgLine(int arg0, float arg1, RelativeLayout.LayoutParams lp) {


        if (mCurrentIndex == 0 && arg0 == 0)// 0->1
        {
            lp.leftMargin = (int) (arg1 * (mWindowsWidth / 2) + 60);
        } else if (mCurrentIndex == 1 && arg0 == 0) // 1->0
        {
            lp.leftMargin = (int) (-(1 - arg1) * (mWindowsWidth / 2) + mCurrentIndex
                    * (mWindowsWidth / 2 + 90));
        } else if (mCurrentIndex == 1 && arg0 == 1) // 1->2
        {
            lp.leftMargin = (int) ((mWindowsWidth * 1.0 / 2) + 90);
        }
        mImgLine.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.my_collection_linearlayout) {
            mViewPage.setCurrentItem(0);

        } else if (i == R.id.my_make_linearlayout) {
            mViewPage.setCurrentItem(1);

        }
    }
}
