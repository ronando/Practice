package com.hkjc.customizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by jesse on 20/5/2016.
 */
public class RandomView extends View {

    protected String mContentText;
    protected float mContentDimention;
    protected int mContentColor;
    private Rect mBounds;
    private Random mRandom;


    protected Paint mPaint;

    public RandomView(Context context) {
        this(context,null);
    }

    public RandomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RandomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        mRandom = new Random();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RandomView,defStyleAttr,0);
        mContentText = typedArray.getString(typedArray.getIndex(R.styleable.RandomView_contentText));
        float defaultSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics());
        mContentDimention = typedArray.getDimension(typedArray.getIndex(R.styleable.RandomView_contentSize),(int)defaultSize);
        mContentColor = typedArray.getColor(typedArray.getIndex(R.styleable.RandomView_contentColor), Color.BLACK);
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mContentDimention);
        mBounds = new Rect();
        mPaint.getTextBounds(mContentText,0,mContentText.length(),mBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int width;
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            width = getPaddingLeft() + getPaddingRight() + mBounds.width();
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST){
            height = getPaddingTop() + getPaddingBottom() + mBounds.height();
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0,0,getWidth() - mBounds.width()/2,getHeight()/2 + mBounds.height()/2,mPaint);
        mPaint.setColor(mContentColor);
        canvas.drawText(mContentText,0,mContentText.length(),getWidth()/2 - mBounds.width()/2,getHeight()/2 + mBounds.height()/2,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            mContentText = "" + mRandom.nextInt();
            postInvalidate();
        }


        return super.onTouchEvent(event);
    }
}
