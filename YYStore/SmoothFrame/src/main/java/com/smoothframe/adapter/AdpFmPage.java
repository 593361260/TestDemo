package com.smoothframe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
public class AdpFmPage extends FragmentPagerAdapter {
    FragmentManager fm;
    Fragment[] fragments;
    boolean[] fragmentsUpdateFlag;

    public void setmPageTitle(List<String> mPageTitle) {
        this.mPageTitle = mPageTitle;
    }
    private List<String> mPageTitle;
    @Override
    public CharSequence getPageTitle(int position) {
        if (null != mPageTitle)
            return mPageTitle.get(position);//页卡标题
        else
            return "" + position;
    }

    public AdpFmPage(FragmentManager fm, Fragment[] fragments, boolean[] fragmentsUpdateFlag) {
        super(fm);
        this.fm = fm;
        this.fragmentsUpdateFlag = fragmentsUpdateFlag;
        this.fragments = fragments;
    }


    @Override
    public int getCount() {
        return fragments.length;
    }



    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments[position % fragments.length];
        Log.i("tag", "getItem:position=" + position + ",fragment:"
                + fragment.getClass().getName() + ",fragment.tag="
                + fragment.getTag());
        return fragments[position % fragments.length];
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
//得到tag，这点很重要
        String fragmentTag = fragment.getTag();


        if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
//如果这个fragment需要更新

            FragmentTransaction ft = fm.beginTransaction();
//移除旧的fragment
            ft.remove(fragment);
//换成新的fragment
            fragment = fragments[position % fragments.length];
//添加新fragment时必须用前面获得的tag，这点很重要
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commit();

//复位更新标志
            fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
        }

        return fragment;
    }
}
