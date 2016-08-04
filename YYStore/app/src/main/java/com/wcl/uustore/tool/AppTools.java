package com.wcl.uustore.tool;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by DoctorY on 2016/3/1.
 * 工具类
 */
public class AppTools {

    private static int WINDOWS_WIDTH = 0;
    private static int WINDOWS_HEIGHT = 0;
    private static WindowManager sManager;

    /**
     * 根据路径压缩图片
     */
    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 压根据Bitmap压缩图片
     */
    private Bitmap compressImage(Bitmap image) {

        if (image == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 2000) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 直接压缩Bitmap对象  效率低点
     *
     * @param image
     * @return
     */
    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 获取顶部状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取底部 navigationbar 高度
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
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

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    /**
     * 缩放Bitmap
     *
     * @param bitmap
     * @param scaleX
     * @param scaleY
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getWindowsWidth(Activity activity) {
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

    /**
     * 获取屏幕的高度
     */

    public static int getWindowsHeight(Activity activity) {
        try {
            if (WINDOWS_HEIGHT == 0) {
                DisplayMetrics dpMetrics = new DisplayMetrics();
                activity.getWindow().getWindowManager().getDefaultDisplay()
                        .getMetrics(dpMetrics);
                WINDOWS_HEIGHT = dpMetrics.heightPixels;
            }
            return WINDOWS_HEIGHT;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取手机屏幕密度dpi
     *
     * @param activity
     * @return
     */
    public static int getWindiwsDensity(Activity activity) {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        int density = dpMetrics.densityDpi;
        return density;
    }

    /**
     * MD5转换
     *
     * @param plainText
     * @return
     */
    public static String Md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    /**
     * 转成int数据  数据的封装
     *
     * @param obj
     * @return
     */
    public static int objToInt(Object obj) {
        int rntValue = 0;
        try {
            if (obj != null) {
                if (obj instanceof Float) {
                    rntValue = ((Float) obj).intValue();
                } else if (obj instanceof Double) {
                    rntValue = ((Double) obj).intValue();
                } else {
                    rntValue = Integer.parseInt(obj.toString());
                }

            }
        } catch (Exception e) {
        }

        return rntValue;
    }


    /**
     * 计算状态栏颜色
     *
     * @param color color值 颜色值
     * @param alpha alpha值 0-255 之间的值
     * @return 最终的状态栏颜色
     */
    public static int calculateStatusColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 计算状态栏颜色
     *
     * @param beforeColor 未改变color值 颜色值
     * @param afterColor  改变之后的颜色值
     * @param alpha       alpha值 0-255 之间的值
     * @return 最终的状态栏颜色
     */
    public static int calculateStatusColor(int beforeColor, int afterColor, float alpha) {
        float a = 1 - alpha / 255f;
        int red1 = beforeColor >> 16 & 0xff;//原始颜色
        int green1 = beforeColor >> 8 & 0xff;//原始颜色
        int blue1 = beforeColor & 0xff;//原始颜色

        int red2 = afterColor >> 16 & 0xff;//需要改成的颜色值
        int green2 = afterColor >> 8 & 0xff;//需要改成的颜色值
        int blue2 = afterColor & 0xff;//需要改成的颜色值

        int d_Red = red1 - red2;//颜色的差值
        int d_Green = green1 - green2;//颜色的差值
        int d_Blue = blue1 - blue2;//颜色的差值
        d_Red = (int) (d_Red * a + 0.5);
        d_Green = (int) (d_Green * a + 0.5);
        d_Blue = (int) (d_Blue * a + 0.5);

        /*red1 = (int) (red1 * a + 0.5);
        green1 = (int) (green1 * a + 0.5);
        blue1 = (int) (blue1 * a + 0.5);*/
        Log.d("AppTools", "R " + (red1 + d_Red) + "  g " + (green1 + d_Green) + "  b  " + (blue1 + d_Blue));
        Log.d("AppTools", "色值   " + (0xff << 24 | (red1 + d_Red) << 16 | (green1 + d_Green) << 8 | (blue1 + d_Blue)));


        return 0xff << 24 | (red1 + d_Red) << 16 | (green1 + d_Green) << 8 | (blue1 + d_Blue);
    }


    /**
     * 对应的版本名称
     *
     * @param versionCode
     * @return
     */
    public static String getAndroidVersion(int versionCode) {
        String str = "";
        switch (versionCode) {
            case 1:
                str = "Android  1.0";
                break;

            case 2:
                str = "Android  1.1";
                break;
            case 3:
                str = "Android  1.5";
                break;
            case 4:
                str = "Android  1.6";
                break;
            case 5:
                str = "Android  2.0";
                break;
            case 6:
                str = "Android  2.0.1";
                break;
            case 7:
                str = "Android  2.1";
                break;
            case 8:
                str = "Android  2.2";
                break;
            case 9:
                str = "Android  2.3";
                break;
            case 10:
                str = "Android  2.3.7";
                break;
            case 11:
                str = "Android  3.0";
                break;
            case 12:
                str = "Android  3.1";
                break;
            case 13:
                str = "Android  3.2";
                break;
            case 14:
                str = "Android  4.0";
                break;
            case 15:
                str = "Android  4.0.3";
                break;
            case 16:
                str = "Android  4.1.0";
                break;
            case 17:
                str = "Android  4.2.0";
                break;
            case 18:
                str = "Android  4.3.1";
                break;
            case 19:
                str = "Android  4.4.2";
                break;
            case 20:
                str = "Android  4.4W.2";
                break;
            case 21:
                str = "Android  5.0.1";
                break;
            case 22:
                str = "Android  5.1";
                break;
            default:
                str = "未检测到最低版本号";
                break;
        }
        return str;
    }
}
