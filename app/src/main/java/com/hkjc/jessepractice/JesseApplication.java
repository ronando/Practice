package com.hkjc.jessepractice;

import android.app.Application;
import android.content.res.Configuration;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jesse on 18/12/15.
 */
public class JesseApplication extends Application {
    public static final String TAG = "JesseApplication";

    public List<String> mExistActivitys = new LinkedList<String>();

    @Override
    public void onCreate() {
        //Load build config
        String buildType = getResources().getString(R.string.build_type);
        Log.d(TAG, "build type is :" + buildType);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        super.onCreate();
        mExistActivitys.add("process aaa");
        Log.d(TAG, "onCreate() process id: " + android.os.Process.myPid());
        Log.d(TAG, "onCreate() : " + mExistActivitys);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.d(TAG, "uncaughtException() jesse catch the exception-begin");
                ex.printStackTrace();
                try {
                    Process process = Runtime.getRuntime().exec("logcat -d");
                    InputStream is = process.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "uncaughtException() jesse catch the exception-end");
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate()");
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged()");
    }
}
