package com.smoothframe.http;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * 图片缓存类
 * Created by Onlydyf on 2015/8/10.
 */
public class ImgCache implements ImageLoader.ImageCache{

    private LruCache<String,Bitmap> lruCache;

    /**
     * 构造方法
     */
    public ImgCache(){
        if (lruCache==null){
            /** 获取系统分配给每个应用程序的最大内存,并把系统内存的1/5分配给lruCache */

            int maxSize=(int)(Runtime.getRuntime().maxMemory()/1024/5);
            lruCache=new LruCache<String,Bitmap>(maxSize){
                /** 测量Bitmap的大小 */
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes()*value.getHeight();
                }
            };
        }
    }
    @Override
    public Bitmap getBitmap(String s) {
        return lruCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        lruCache.put(s,bitmap);
    }
}
