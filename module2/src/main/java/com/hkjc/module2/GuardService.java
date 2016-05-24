package com.hkjc.module2;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.os.Process;

/**
 * Created by jesse on 22/12/15.
 */
public class GuardService extends Service {
    public static final String TAG = "GuardService";


    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("GuardServiceHandlerThread",Process.THREAD_PRIORITY_BACKGROUND);
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

}
