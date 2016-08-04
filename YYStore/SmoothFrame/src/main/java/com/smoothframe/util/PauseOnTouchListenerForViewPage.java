package com.smoothframe.util;

import android.view.MotionEvent;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * ViewPage滑动时停止图片的加载。
 */
public class PauseOnTouchListenerForViewPage implements View.OnTouchListener {
    private ImageLoader imageLoader;

    public PauseOnTouchListenerForViewPage(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imageLoader.pause();
                break;
            case MotionEvent.ACTION_UP:
                imageLoader.resume();
                break;
        }
        return false;
    }
}
