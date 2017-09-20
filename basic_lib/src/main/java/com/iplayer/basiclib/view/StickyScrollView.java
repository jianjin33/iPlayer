package com.iplayer.basiclib.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.ScrollView;

import static com.iplayer.basiclib.util.UIUtils.dp2px;

/**
 * Created by Administrator on 2017/9/20.
 * 上下边界粘性,多点触控且具有回弹效果的ScrollView
 */

public class StickyScrollView extends ScrollView {

    private View mTargetView;
    private Point mOriginPos = new Point();
    private int activePointerId;
    private float startY, mLastY, moveY;
    private Rect normal = new Rect();
    private OverScroller mOverScroller;
    private boolean canScroll;
    private GestureDetector mGestureDetector;

    public StickyScrollView(Context context) {
        this(context, null);
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        mOverScroller = new OverScroller(context, interpolator);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        canScroll = true;
        setVerticalScrollBarEnabled(false);
//        setScrollbarFadingEnabled(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            mTargetView = getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mTargetView != null) {
            mOriginPos.x = mTargetView.getLeft();
            mOriginPos.y = mTargetView.getTop();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(0);
                startY = mLastY = ev.getY(0);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private final int INVALID_POINTER = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTargetView == null)
            return false;

        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // 将新落下的触点作为活动触点 actionIndex 0 --> 1 activePointerId始终不会改变 actionIndex会根据触点数改变。
                activePointerId = event.getPointerId(actionIndex);
                mLastY = event.getY(actionIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (activePointerId == event.getPointerId(actionIndex)) {
                    // 松开的为活动触点
                    final int newPointerIndex = actionIndex == 0 ? 1 : 0;
                    activePointerId = event.getPointerId(newPointerIndex);
                    mLastY = event.getY(newPointerIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                activePointerId = INVALID_POINTER;
                if (mOnPullListener != null && mTargetView != null) {
                    // 往下拉的距离超过了100dp
                    if (mTargetView.getTop() - mOriginPos.y > dp2px(100)) {
                        mOnPullListener.onDownPull();
                    } else if (mOriginPos.y - mTargetView.getTop() > dp2px(100)) { // 往上拉的距离超过了100dp
                        mOnPullListener.onUpPull();
                    }
                }
                if (!normal.isEmpty()) {
                    mOverScroller.startScroll(mTargetView.getLeft(), mTargetView.getTop(), 0, mOriginPos.y - mTargetView.getTop(), 200);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (activePointerId == INVALID_POINTER) {
                    activePointerId = event.getPointerId(actionIndex);
                }
                final int pointerIndex = event.findPointerIndex(activePointerId);
                float currentY = event.getY(pointerIndex);
                // 假如是下拉, currentY > perY, offset > 0
                int offset = (int) (currentY - mLastY);
                mLastY = currentY;
                int deltaY = Math.abs(mTargetView.getTop() - mOriginPos.y);
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        normal.set(mTargetView.getLeft(), mTargetView.getTop(), mTargetView.getRight(), mTargetView.getBottom());
                    }
                    ViewCompat.offsetTopAndBottom(mTargetView, calculateNewOffset(deltaY, offset));
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 每隔多少距离就开始增大阻力系数, 数值越小阻力就增大的越快
     */
    private final int LENGTH = 150;
    /**
     * 阻力系数, 越大越难拉
     */
    private int mFraction = 2;

    private int calculateNewOffset(int deltaY, int offset) {
        int newOffset = offset / (mFraction + deltaY / LENGTH);
        if (newOffset == 0) {
            newOffset = offset >= 0 ? 1 : -1;
        }

        return newOffset;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mOverScroller.computeScrollOffset()) {
            ViewCompat.offsetTopAndBottom(mTargetView, mOverScroller.getCurrY() - mTargetView.getTop());
            invalidate();
        }

    }

    public boolean isNeedMove() {
        int offset = mTargetView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    public interface OnPullListener {
        void onDownPull();

        void onUpPull();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            canScroll = true;
        }
        return mGestureDetector.onTouchEvent(ev);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (canScroll)
                if (Math.abs(distanceY) >= Math.abs(distanceX))
                    canScroll = true;
                else
                    canScroll = false;

            if (canScroll) {
                if (e1.getAction() == MotionEvent.ACTION_DOWN) {
                    moveY = 0;
                    startY = e1.getY();
                }
            }
            return canScroll;
        }
    }


    private OnPullListener mOnPullListener;

    public void setOnPullListener(OnPullListener listener) {
        mOnPullListener = listener;
    }
}
