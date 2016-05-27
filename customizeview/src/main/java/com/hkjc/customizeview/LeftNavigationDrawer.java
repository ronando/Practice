package com.hkjc.customizeview;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jesse on 26/5/2016.
 */
public class LeftNavigationDrawer extends ViewGroup {
    protected View mDrawerView;
    protected View mContentView;
    protected ViewDragHelper mDragHelper;
    protected float mRatioMenuOnScreen = 0;


    public LeftNavigationDrawer(Context context) {
        this(context, null);
    }

    public LeftNavigationDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public LeftNavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDrawerView;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                mDragHelper.captureChildView(mDrawerView, pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return Math.min(Math.max(-child.getMeasuredWidth(), left), 0);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                int childWidth = changedView.getWidth();
                mRatioMenuOnScreen = (childWidth + left)/childWidth;
                invalidate();
            }
        });

        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        MarginLayoutParams lp = (MarginLayoutParams) mDrawerView.getLayoutParams();
        //measure drawer view
        int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec, lp.leftMargin + lp.rightMargin + getPaddingLeft() + getPaddingRight(), lp.width);
        int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin + getPaddingTop() + getPaddingBottom(), lp.height);
        mDrawerView.measure(drawerWidthSpec, drawerHeightSpec);

        //measure content view
        lp = (MarginLayoutParams) mContentView.getLayoutParams();
        int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize - lp.leftMargin - lp.rightMargin - getPaddingRight() - getPaddingLeft(), MeasureSpec.EXACTLY);
        int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize - lp.topMargin - lp.bottomMargin - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY);
        mContentView.measure(contentWidthSpec, contentHeightSpec);
    }

    /**
     * {@inheritDoc}
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams) mContentView.getLayoutParams();
        mContentView.layout(l + getPaddingLeft() + lp.leftMargin
                , t + getPaddingTop() + lp.topMargin
                , l + getPaddingLeft() + lp.leftMargin + mContentView.getMeasuredWidth()
                , t + getPaddingTop() + lp.topMargin + mContentView.getMeasuredHeight());
        lp = (MarginLayoutParams) mDrawerView.getLayoutParams();
        int menuWidth = mDrawerView.getMeasuredWidth();
        int drawerLeft = -menuWidth + (int)(menuWidth * mRatioMenuOnScreen);
        mDrawerView.layout(drawerLeft, lp.topMargin, drawerLeft + menuWidth, lp.topMargin + mDrawerView.getMeasuredHeight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mDrawerView = getChildAt(1);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }
}
