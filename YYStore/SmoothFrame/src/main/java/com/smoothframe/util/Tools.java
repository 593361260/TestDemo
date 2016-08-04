package com.smoothframe.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.sjkj.myapplication.R;

/**
 * Created by Administrator on 2015/8/13 0013.
 */
public class Tools {
	private static ImageLoaderConfiguration config = null;
	public static DisplayImageOptions options = null;
	public static AbsListView.OnScrollListener onScrollListener = null;
	private static Snackbar mSnackbar = null;
	private static int WINDOWS_WIDTH = 0;

	public static void SnackBarShow(View v, String text, String actionText, View.OnClickListener clickListener) {
		try {
			if (null == mSnackbar || mSnackbar.getView() != v) {
				mSnackbar = Snackbar.make(v, text, Snackbar.LENGTH_SHORT)
						.setAction(actionText, clickListener);
			} else {
				mSnackbar.setText(text);
				mSnackbar.setAction(actionText, clickListener);
			}
			mSnackbar.show();
		} catch (Exception e) {
		}
	}

	public static void initDisplayImageOptions() {
		if (null != options) {
			return;
		}
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.mipmap.default_bg)//加载过程中显示的图片
				.showImageForEmptyUri(R.mipmap.default_bg)//加载内容为空显示的图片
				.showImageOnFail(R.mipmap.default_bg)//加载失败显示的图片
				.cacheInMemory(true)//启用内存缓存
				.cacheOnDisk(true)//启用硬盘缓存
				.considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
//                .delayBeforeLoading(1000)//设置延迟部分时间再开始加载
				.bitmapConfig(Bitmap.Config.RGB_565)    //设置图片的解码类型
						//        ALPHA_8 代表8位Alpha位图
						//        ARGB_4444 代表16位ARGB位图，由4个4位组成
						//        ARGB_8888 代表32位ARGB位图，由4个8位组成
						//        RGB_565 代表16位RGB位图，R为5位，G为6位，B为5位
//                .displayer(new FadeInBitmapDisplayer(1500))//是否图片加载好后渐入的动画时间
				.displayer(new RoundedBitmapDisplayer(20))//圆角
				.imageScaleType(ImageScaleType.EXACTLY)
//                .displayer(new FadeInBitmapDisplayer(388))
				.build();
	}


	public static void initImageLoader(Context context) {
		if (null != config) {
			return;
		}
		config = new ImageLoaderConfiguration.Builder(context)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSizePercentage(13)
				.diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileCount(200)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.threadPriority(Thread.MAX_PRIORITY)
				.imageDownloader(new BaseImageDownloader(context))
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		try {
			final float scale = context.getResources().getDisplayMetrics().density;
			return (int) (dpValue * scale + 0.5f);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		try {
			final float scale = context.getResources().getDisplayMetrics().density;
			return (int) (pxValue / scale + 0.5f);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static float getWindowsWidth(Activity activity) {
		try {
			if (WINDOWS_WIDTH == 0) {
				DisplayMetrics dpMetrics = new DisplayMetrics();
				activity.getWindow().getWindowManager().getDefaultDisplay()
						.getMetrics(dpMetrics);
				WINDOWS_WIDTH = dpMetrics.widthPixels;
			}
			return WINDOWS_WIDTH;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}

}
