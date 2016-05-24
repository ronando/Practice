package com.hkjc.jessepractice.service;

import android.os.HandlerThread;
import android.util.Log;

/**
 * Created by jesse on 27/1/16.
 */
public class PracticeHandlerThread extends HandlerThread {
    public static final String TAG = "PracticeHandlerThread";
    public PracticeHandlerThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        try {
            //do some work
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "PracticeHandlerThread-run()");
    }
}
