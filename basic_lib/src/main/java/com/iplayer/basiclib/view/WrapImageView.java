package com.iplayer.basiclib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.iplayer.basiclib.R;

/**
 * Created by Administrator on 2017/10/9.
 */

@SuppressLint("AppCompatCustomView")
public class WrapImageView extends ImageView {

    private double ratio;

    public WrapImageView(Context context) {
        this(context, null);
    }

    public WrapImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WrapImageView);
        //开关的背景和按钮图片
        ratio = typedArray.getFloat(R.styleable.WrapImageView_ratio, 1.0f);
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        // 通过宽度与高度的比例得到高度
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize * ratio), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
