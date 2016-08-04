package com.smoothframe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动换行布局
 */
public class AutoLineLayout extends ViewGroup {

    public static int ROW = 0;
    private int maxWidth;// 可使用的最大宽度

    public AutoLineLayout(Context context) {
        super(context);
    }

    public AutoLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoLineLayout(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 1;// 子组件行数,初始化赋值为1
        int left = 0;// 子组件的左边“坐标”
        int right = 0;// 子组件的右边“坐标”

        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                int p = getPaddingLeft();
                y = row * height + height;
                left = p + right;// ---------------------------------------------------备注1
                right = left + width;// -----------------------------------------------备注2
                if (right > maxWidth) {
                    row++;
                    left = 0;//每次换行后要将子组件左边“坐标”与右边“坐标”重新初始化
                    right = 0;
                    left = p + right;
                    right = left + width;
                    y = row * height + height;
                }
            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();// 获取子组件数
        int row = 1;// 子组件行数,初始化赋值为1
        int left = 0;// 子组件的左边“坐标”
        int right = 0;// 子组件的右边“坐标”
        int top = 0;// 子组件的顶部“坐标”

        int bottom = 0;// 子组件的底部“坐标”
        int p = getPaddingLeft();// 在父组件中设置的padding属性的值,该值显然也会影响到子组件在屏幕的显示位置
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();// 测量子组件的宽
            int height = view.getMeasuredHeight();// 测量子组件的高
            left = p + right;// ---------------------------------------------------备注1
            right = left + width;// -----------------------------------------------备注2
            top = p * row+   height * (row - 1);// ---------------------------------备注3
            bottom = top + height;// ----------------------------------------------备注4
            if (right > maxWidth) {
                row++;
                left = 0;//每次换行后要将子组件左边“坐标”与右边“坐标”重新初始化
                right = 0;
                left = p + right;
                right = left + width;
                top = p * row + height * (row - 1);
                bottom = top+ height;
            }
            view.layout(left, top-10, right, bottom-10);// 最后按照计算出来的“坐标”将子组件放在父容器内
        }
    }

}