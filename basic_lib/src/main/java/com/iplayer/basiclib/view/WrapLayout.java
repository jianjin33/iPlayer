package com.iplayer.basiclib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.iplayer.basiclib.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 */

public class WrapLayout extends ViewGroup {


    private int childCount;

    public WrapLayout(Context context) {
        super(context);
    }

    public WrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得宽高
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        LogUtils.d(this, sizeWidth + "," + sizeHeight);

        // wrap_content情况需要自己实现
        int height = 0;
        int width = 0;

        // 记录子类宽高
        int lineWidth = 0;
        int lineHeight = 0;

        childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth) {
                // 达到最大宽度
                // 设置最大的宽度给width
                width = Math.max(lineWidth, childWidth);

                lineWidth = childWidth; // 重新开启新行，开始记录
                height += lineHeight;
                lineHeight = childHeight;   // 记录下一行高度
            } else {
                // 子类宽度没有超过
                lineWidth += childWidth;
                lineHeight = childHeight;
            }

            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        LogUtils.d(this,"width:" + width + "height:" + height);

        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width
                , (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    private List<List<View>> mAllViews = new ArrayList<List<View>>();

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int width = getWidth();

        mAllViews.clear();

        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();

        int lineWidth = 0;
        int lineHeight = 0;
        for (int j = 0; j < childCount; j++) {
            View child = getChildAt(j);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
            lineViews.add(child);
        }

        // 记录最后一行
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();
        for (int j = 0; j < lineNums; j++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(j);
            // 遍历当前行所有的View
            for (int h = 0; h < lineViews.size(); h++) {
                View child = lineViews.get(h);
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                // 计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            // 下一行
            left = 0;
            top += lineHeight;
        }
    }

    // 与当前ViewGroup对应的LayoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        // TODO Auto-generated method stub
        return new MarginLayoutParams(getContext(), attrs);
    }


    public void setView(List<View> views, MarginLayoutParams params) {
        for (View view : views) {
            addView(view, params);
        }
    }


}
