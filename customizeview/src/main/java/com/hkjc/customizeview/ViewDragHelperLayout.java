package com.hkjc.customizeview;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by jesse on 24/5/2016.
 */
public class ViewDragHelperLayout extends LinearLayout {
    private View mAutoBackView;
    private View mEdgeView;
    private View mAnywayView;
    private Point mOriginalPosition;
    private ViewDragHelper mDragHelper;

    public ViewDragHelperLayout(Context context) {
        this(context,null);
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mOriginalPosition = new Point();
        mDragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return (child == mAutoBackView || child == mAnywayView) ;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                mDragHelper.captureChildView(mEdgeView,pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if(releasedChild == mAutoBackView){
                    mDragHelper.settleCapturedViewAt(40,40);
                    invalidate();
                }
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return Math.min(getHeight(),Math.max(100,top));
            }


        });
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mOriginalPosition.x = mAutoBackView.getLeft();
        mOriginalPosition.y = mAutoBackView.getTop();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAutoBackView = getChildAt(0);
        mEdgeView = getChildAt(1);
        mAnywayView = getChildAt(2);
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
