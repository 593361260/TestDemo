package com.wcl.uustore.factory;

import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

import com.wcl.uustore.frag.RecommendGame;

/**
 * Created by DoctorY on 2016/4/13.
 */
public class FragmentFactory {

    private SparseArrayCompat<Fragment> caches = new SparseArrayCompat<Fragment>();

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        Fragment cacheFragment = caches.get(position);
        if (null != cacheFragment) {
            return cacheFragment;
        }
        switch (position) {
            case 0:
                fragment = new RecommendGame();
                break;
            case 1:
                fragment = new RecommendGame();
                break;
            case 2:
                fragment = new RecommendGame();
                break;
        }
        caches.put(position, fragment);
        return fragment;
    }
}
