package com.hkjc.customizeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by jesse on 23/5/2016.
 */
public class ProgressView extends View {

    protected Paint mPaint;
    private int mProgress;
    private Thread mProgressThread;

    private int center;
    private int strokeWidth;
    private int radius;
    private RectF oval;


    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgress = 0;
        mProgressThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    postInvalidate();
                    if (mProgress == 360) {
                        mProgress = 0;
                    }
                    try {
                        Log.d("progress thread", "run()");
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        mProgressThread.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        center = getWidth() / 2;
        strokeWidth = 20;
        radius = 100;
        oval = new RectF(center - radius, center - radius, center + radius, center + radius);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, radius, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawArc(oval, -90, mProgress, false, mPaint);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mProgressThread.isInterrupted()){
            mProgressThread.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mProgressThread.isAlive()){
            mProgressThread.interrupt();
        }
    }
}
