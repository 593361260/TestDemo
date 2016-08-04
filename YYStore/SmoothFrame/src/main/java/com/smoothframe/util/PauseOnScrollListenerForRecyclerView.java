package com.smoothframe.util;

import android.support.v7.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2015/8/26 0026.
 */
public class PauseOnScrollListenerForRecyclerView extends RecyclerView.OnScrollListener {
    private ImageLoader imageLoader;
    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    private final RecyclerView.OnScrollListener externalListener;

    public PauseOnScrollListenerForRecyclerView(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
        this(imageLoader, pauseOnScroll, pauseOnFling, (RecyclerView.OnScrollListener)null);
    }

    public PauseOnScrollListenerForRecyclerView(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling, RecyclerView.OnScrollListener customListener) {
        this.imageLoader = imageLoader;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = customListener;
    }
    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {
        switch(scrollState) {
            case 0:
//                Log.i("onScrollStateChanged","停止");
                this.imageLoader.resume();
                break;
            case 1:
//                Log.i("onScrollStateChanged", "慢");
                if (pauseOnScroll) {
                    imageLoader.pause();
                } else {
                    imageLoader.resume();
                }
                break;
            case 2:
//                Log.i("onScrollStateChanged","快");
                if (pauseOnFling) {
                    imageLoader.pause();
                } else {
                    imageLoader.resume();
                }
                break;

        }

        if(this.externalListener != null) {
            this.externalListener.onScrollStateChanged(view, scrollState);
        }

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(this.externalListener != null) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
