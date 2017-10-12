package com.iplayer.mine;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.iplayer.basiclib.util.LogUtils;

/**
 * Created by Administrator on 2017/10/12.
 */

public class PlayButton extends View {

    private static final int PLAY = 0;
    private static final int PAUSE = 1;
    private final int strokeWidth = 30;

    private Paint paint;
    private Path path;
    private Path path2;
    private Path path3;

    private ValueAnimator mStartingAnimator;
    private long defaultDuration = 2000;
    private float mAnimatorValue;
    private PathMeasure pathMeasure;
    private int state = -1;
    private RectF mRectF;
    private Path circlePath;
    private Paint painTest;
    private PathMeasure circlePathMeasure;
    private Path circleDst;
    private Path dst;

    public PlayButton(Context context) {
        this(context, null);
    }

    public PlayButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setClickable(true);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.parseColor("#00FF00"));
        paint.setStrokeCap(Paint.Cap.ROUND);  // 实现末端圆弧

        painTest = new Paint();
        painTest.setAntiAlias(true);
        painTest.setStyle(Paint.Style.STROKE);
        painTest.setStrokeWidth(strokeWidth);
        painTest.setColor(Color.parseColor("#FF0000"));
        painTest.setStrokeCap(Paint.Cap.ROUND);  // 实现末端圆弧

        path = new Path();
        path.moveTo(50, 100);
        path.lineTo(50, 250);

        path2 = new Path();
        path2.moveTo(120, 100);
        path2.lineTo(120, 250);

        path3 = new Path();
        path3.moveTo(50, 100);
        path3.lineTo(150, 175);
        path3.lineTo(50, 250);
        path3.close();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

        circlePath = new Path();
        mRectF = new RectF(50, 240, 120, 240 + 75 / 2 + strokeWidth / 2);
        circlePath.addArc(mRectF, 0, 180);

        circlePathMeasure = new PathMeasure(circlePath, false);
        circleDst = new Path();

        pathMeasure = new PathMeasure(path3, false);
        dst = new Path();


        initAnimator();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.drawPath(path, paint);
        canvas.drawPath(path2, paint);
        canvas.restore();


//        canvas.drawPath(circlePath, paint);

        switch (state) {
            case PLAY:
                path2.reset();
                // 三角形测试
//        path.rewind();
//        pathMeasure.getSegment(pathMeasure.getLength() * mAnimatorValue, pathMeasure.getLength(), dst, true);
                pathMeasure.getSegment(0, pathMeasure.getLength() * mAnimatorValue, dst, true);
                canvas.drawPath(dst, paint);

                LogUtils.e(this, "circlePathMeasure.getLength():" + circlePathMeasure.getLength());
//                circlePathMeasure.getSegment(circlePathMeasure.getLength() - circlePathMeasure.getLength() * (1 - mAnimatorValue),
//                        circlePathMeasure.getLength() * mAnimatorValue,
//                        circleDst, true);
//                canvas.drawPath(circleDst, paint);

                float circleValue = mAnimatorValue + 0.4f;
                if (mAnimatorValue  >= 1)
                    circleValue = 1;

                float stop = circlePathMeasure.getLength() * circleValue;
                float start = (float) (stop - ((0.5 - Math.abs(circleValue - 0.5)) * 200f));
                circleDst.reset();
                circlePathMeasure.getSegment(start, stop, circleDst, true);
                canvas.drawPath(circleDst, paint);

                break;
            case PAUSE:
                break;
        }


    }


    private void initAnimator() {
        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);

        mStartingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

    }

    private OnClickListener mListener;
    private onViewClick mViewClick;
    private int startRawX;
    private int startRawY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startRawX = rawX;
                startRawY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if (x + getLeft() < getRight() && y + getTop() < getBottom()) {
                    // 开始
                    dst.reset();
                    circleDst.reset();
                    state = PLAY;
                    mStartingAnimator.start();

                    if (mListener != null)
                        mListener.onClick(this);
                    if (mViewClick != null)
                        mViewClick.onClick(rawX - startRawX, rawY - startRawY);
                }
                break;
        }
        return true;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mListener = l;
    }

    public void setOnViewClick(onViewClick click) {
        this.mViewClick = click;
    }


    public interface onViewClick {
        /**
         * @param scrollX 从按下到抬起,X轴方向移动的距离
         * @param scrollY 从按下到抬起,Y轴方向移动的距离
         */
        void onClick(float scrollX, float scrollY);
    }
}
