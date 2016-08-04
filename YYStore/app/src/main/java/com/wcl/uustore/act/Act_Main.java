package com.wcl.uustore.act;

import android.content.Intent;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wcl.uustore.R;
import com.wcl.uustore.baseAct.BaseAct;
import com.wcl.uustore.factory.FragmentFactory;
import com.wcl.uustore.tool.AppConfig;
import com.wcl.uustore.tool.AppTools;
import com.wcl.uustore.widget.LivingTabsLayout;

import java.util.ArrayList;
import java.util.List;

public class Act_Main extends BaseAct implements View.OnClickListener {
    private String tag = "Act_Main";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Button mBtn_close;
    private EditText mEdit_search;
    private Button mBtn_search;
    private FragmentFactory mFactory;
    private AppBarLayout mAppBarLayout;
    private int mStatHeight;
    private LinearLayout mLinear_drag;

    @Override
    protected void getMessage(Message msg) {

    }

    @Override
    public int getLayoutId() {
        // 设了默认的windowBackground使得冷启动没那么突兀，这里再设置为空减少过度绘制
        getWindow().setBackgroundDrawable(null);
        return R.layout.act_main;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mBtn_close = (Button) findViewById(R.id.btn_close);
        mEdit_search = (EditText) findViewById(R.id.edit_search);
        mBtn_search = (Button) findViewById(R.id.btn_search);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mLinear_drag = (LinearLayout) findViewById(R.id.linear_drag);
        mBtn_search.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mStatHeight = AppTools.getStatusBarHeight(this);
        initConfig();
        mFactory = new FragmentFactory();
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragAndName("推荐");
        adapter.addFragAndName("游戏");
        adapter.addFragAndName("应用");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int padding = (int) Math.abs((mStatHeight * (verticalOffset * 1.0f / 180.0f) + 0.5f));
                mLinear_drag.setPadding(0, 0, 0, padding);
                int color = (int) Math.abs((50 * (verticalOffset * 1.0f / 180.0f) + 0.5f));
                if (verticalOffset == 0) {
//                    mAppBarLayout.setBackgroundColor(Color.TRANSPARENT);
                } else {
//                    mAppBarLayout.setBackgroundColor(calculateStatusColor(getResources().getColor(R.color.transparent_blue), color));
                }
//                Log.d(tag, "verticalOffset   " + verticalOffset + "   padding  " + padding + "  color  " + color);
            }
        });
    }

    private void initConfig() {
        AppConfig.SCREENTYPE = AppTools.getWindiwsDensity(this) + "";
        AppConfig.SCREENSIZE = AppTools.getWindowsHeight(this) + "x" + AppTools.getWindowsWidth(this);
    }

    @Override
    protected void request(int flag) {
    }

    @Override
    public void onClick(View v) {
//        BottomSheetDialogView.show(this,mDayNightMode);
        startActivity(new Intent(this, Act_Detail.class));
    }

    /**
     * Fragment 适配器
     */
    private class Adapter extends FragmentPagerAdapter implements LivingTabsLayout.DrawableResIconAdapter {
        private List<Fragment> mFragments = new ArrayList<>();
        private List<String> mName = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragAndName(String name) {
            mName.add(name);
        }

        @Override
        public Fragment getItem(int position) {
            return mFactory.getFragment(position);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mName.get(position);
        }

        @Override
        public int getIcon(int position) {
            switch (position) {
                case 1:
                    return R.mipmap.iocn_recommend;
                case 0:
                    return R.mipmap.iocn_game;
                case 2:
                    return R.mipmap.iocn_app;
            }
            return -1;
        }
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
