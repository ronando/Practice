package com.hkjc.customizeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jesse on 26/5/2016.
 */
public class LeftNavigationDrawer extends ViewGroup{
    protected View mDrawerView;
    protected View mContentView;


    public LeftNavigationDrawer(Context context) {
        this(context,null);
    }

    public LeftNavigationDrawer(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public LeftNavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        MarginLayoutParams  lp = (MarginLayoutParams) mDrawerView.getLayoutParams();
        //measure drawer view
        int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec,lp.leftMargin + lp.rightMargin +getPaddingLeft()+getPaddingRight(),lp.width);
        int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec,lp.topMargin + lp.bottomMargin + getPaddingTop() + getPaddingBottom(), lp.height);
        mDrawerView.measure(drawerWidthSpec,drawerHeightSpec);

        //measure content view
        lp = (MarginLayoutParams) mContentView.getLayoutParams();
        int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize-lp.leftMargin-lp.rightMargin-getPaddingRight()-getPaddingLeft(),MeasureSpec.EXACTLY);
        int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize-lp.topMargin-lp.bottomMargin-getPaddingTop()-getPaddingBottom(),MeasureSpec.EXACTLY);
        mContentView.measure(contentWidthSpec,contentHeightSpec);
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
        mContentView.layout(l+getPaddingLeft()+lp.leftMargin
                            ,t+getPaddingTop() + lp.topMargin
                            ,r-getPaddingRight()-lp.rightMargin
                            ,b-getPaddingBottom()-lp.bottomMargin);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mDrawerView = getChildAt(1);
    }
}
