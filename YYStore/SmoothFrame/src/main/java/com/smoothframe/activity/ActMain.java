package com.smoothframe.activity;

import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.smoothframe.fragment.FmTwo;
import com.smoothframe.adapter.AdpPager;
import com.smoothframe.base.BaseActivity;
import com.smoothframe.fragment.FmFour;
import com.smoothframe.fragment.FmOne;
import com.smoothframe.fragment.FmThree;
import com.smoothframe.view.DialogDefault;
import com.smoothframe.view.viewpage.JazzyViewPager;
import com.nineoldandroids.view.ViewHelper;
import com.sjkj.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ActMain extends BaseActivity {
    private TabHost tabHost;
    List<Map<String, View>> mTabViews = new ArrayList<Map<String, View>>();
    private JazzyViewPager mViewPager;
    private AdpPager mPagerAdapter;
    private TextView tvTitle;
    private FloatingActionButton mFaBtn;
    private DialogDefault mDialogDefault;
    @Override
    protected void initData() {
        mPagerAdapter = new AdpPager(this);
        mPagerAdapter.addTab(FmOne.class, null);
        mPagerAdapter.addTab(FmTwo.class, null);
        mPagerAdapter.addTab(FmThree.class, null);
        mPagerAdapter.addTab(FmFour.class, null);
        initJazzyPager(JazzyViewPager.TransitionEffect.valueOf("Tablet"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    protected void getMessage(Message msg) {
    }



    @Override
    protected void initView() {
        mFaBtn = (FloatingActionButton) findViewById(R.id.faBtn);
        mFaBtn.setOnClickListener(this);
        mViewPager = (JazzyViewPager) findViewById(R.id.viewPage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        //初始化tab栏
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("0").setIndicator(createTab("表情屋", 0)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("1").setIndicator(createTab("我的表情", 1)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator(createTab("搜索", 2)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator(createTab("更多", 3)).setContent(android.R.id.tabcontent));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int index = Integer.parseInt(tabId);
                setTabSelectedState(index, 4);
                tabHost.getTabContentView().setVisibility(View.GONE);
            }
        });
        tabHost.setCurrentTab(0);
        tabHost.getTabWidget().setDividerDrawable(null);
    }


    /**
     * 主页面更换时更改标题文字
     *
     * @param checkId 对应的底栏按钮id
     */
    private void changeTitle(int checkId) {
        switch (checkId) {
            case 0:
                tvTitle.setText("one");
                break;
            case 1:
                tvTitle.setText("two");
                break;
            case 2:
                tvTitle.setText("three");
                break;
            case 3:
                tvTitle.setText("four");
                break;
            default:
                break;
        }
    }
/**
 * 创建底部tab
 * */
    private View createTab(String tabLabelText, int tabIndex) {
        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tabwidget, null);
        TextView normalTV = (TextView) tabIndicator.findViewById(R.id.tvNormal);
        TextView selectedTV = (TextView) tabIndicator.findViewById(R.id.tvChecked);
        normalTV.setText(tabLabelText);
        selectedTV.setText(tabLabelText);
        ImageView normalImg = (ImageView) tabIndicator.findViewById(R.id.imgNormal);
        ImageView selectedImg = (ImageView) tabIndicator.findViewById(R.id.imgChecked);
        switch (tabIndex) {
            case 0:
                normalImg.setImageResource(R.mipmap.tabbar_icon_1);
                selectedImg.setImageResource(R.mipmap.tabbar_icon_1_sel);
                break;
            case 1:
                normalImg.setImageResource(R.mipmap.tabbar_icon_2);
                selectedImg.setImageResource(R.mipmap.tabbar_icon_2_sel);
                break;
            case 2:
                normalImg.setImageResource(R.mipmap.tabbar_icon_3);
                selectedImg.setImageResource(R.mipmap.tabbar_icon_3_sel);
                break;
            case 3:
                normalImg.setImageResource(R.mipmap.tabbar_icon_4);
                selectedImg.setImageResource(R.mipmap.tabbar_icon_4_sel);
                break;
        }
        View normalLayout = tabIndicator.findViewById(R.id.layNormal);
        normalLayout.setAlpha(1f);
        View selectedLayout = tabIndicator.findViewById(R.id.layChecked);
        selectedLayout.setAlpha(0f);
        Map<String, View> map = new HashMap<String, View>();
        map.put("normal", normalLayout);
        map.put("selected", selectedLayout);
        mTabViews.add(map);
        return tabIndicator;
    }

    private void setTabSelectedState(int index, int tabCount) {
        for (int i = 0; i < tabCount; i++) {
            if (i == index) {
                mTabViews.get(i).get("normal").setAlpha(0f);
                mTabViews.get(i).get("selected").setAlpha(1f);
            } else {
                mTabViews.get(i).get("normal").setAlpha(1f);
                mTabViews.get(i).get("selected").setAlpha(0f);
            }
        }
        mViewPager.setCurrentItem(index);
    }

    private void initJazzyPager(JazzyViewPager.TransitionEffect effect) {
        mViewPager.setTransitionEffect(effect);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageMargin(30);
        mViewPager.setFadeEnabled(true);
        mViewPager.setSlideCallBack(new JazzyViewPager.SlideCallback() {
            @Override
            public void callBack(int position, float positionOffset) {
                Map<String, View> map = mTabViews.get(position);
                ViewHelper.setAlpha(map.get("selected"), positionOffset);
                ViewHelper.setAlpha(map.get("normal"), 1 - positionOffset);
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
                changeTitle(position);
            }

            @Override
            public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
            }

            @Override
            public void onPageScrollStateChanged(int paramInt) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTabSelectedState(tabHost.getCurrentTab(), 4);
    }

    @Override
    public void onClick(View arg0) {
        mDialogDefault = new DialogDefault(this, "default dialog test", "ensure", "cancel", new DialogDefault.OnClickLeft() {
            @Override
            public void clickLeft() {
                Log.i("tag", "left");

            }
        }, new DialogDefault.OnClickRight() {
            @Override
            public void clickRight() {
                Log.i("tag", "right");
                mDialogDefault.dismiss();
            }
        });
        mDialogDefault.setCanceledOnTouchOutside(false);
        if (arg0.getId() == R.id.faBtn) {
            mDialogDefault.show();
        }
    }
}
