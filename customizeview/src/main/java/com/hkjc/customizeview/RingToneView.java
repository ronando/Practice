package com.hkjc.customizeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jesselu on 6/5/16.
 */

public class RingToneView extends View {

    private int mBgWidth = 400;
    private int mBgHeight = 320;
    private int mBgColor = Color.GRAY;
    private int mRingCicleRadius = 150;
    private int mCicleBgColor = Color.BLACK;
    private int mFontColor = Color.WHITE;
    private int mDashCount = 18;
    private int mStrokeWidth = 20;
    private Paint mPaint;
    private RectF mCircleRect;
    private int mCurrentCount;

    private int touchDownY;
    private int touchUpY;


    public RingToneView(Context context) {
        this(context, null);
    }

    public RingToneView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingToneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleRect = new RectF(mBgWidth / 2 - mRingCicleRadius, mBgHeight / 2 - mRingCicleRadius, mBgWidth / 2 + mRingCicleRadius, mBgHeight / 2 + mRingCicleRadius);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw background
        mPaint.setColor(mBgColor);
        mPaint.setAlpha(50);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, mBgWidth, mBgHeight, mPaint);


        //draw background circle
        mPaint.setStrokeWidth(10);
        mPaint.setColor(mCicleBgColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        for (int i = 0; i < mDashCount; i++) {
            canvas.drawArc(mCircleRect, i * 20, 13, false, mPaint);
        }

        mPaint.setColor(mFontColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(mCircleRect, i * 20, 13, false, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touchUpY = (int) event.getY();
                if (touchDownY > touchUpY) {
                    upRingTone();
                } else {
                    downRingTone();
                }
                break;
        }
        return true;
    }


    private void upRingTone() {
        if (mCurrentCount >= mDashCount) return;
        mCurrentCount++;
        postInvalidate();
    }

    private void downRingTone() {
        if (mCurrentCount <= 0) return;
        mCurrentCount--;
        postInvalidate();
    }
}
