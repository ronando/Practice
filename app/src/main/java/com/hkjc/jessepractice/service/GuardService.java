package com.hkjc.jessepractice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.UUID;

/**
 * Created by jesse on 22/12/15.
 */
public class GuardService extends Service {
    public static final String TAG = "GuardService";
    private final Messenger mMessenger = new Messenger(new IncomingHandler());

    private class IncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage()");
            String androidId = Settings.Secure.ANDROID_ID;
            String aaa = UUID.randomUUID().toString();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
        return mMessenger.getBinder();
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
