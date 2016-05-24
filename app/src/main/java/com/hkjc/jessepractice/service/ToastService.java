package com.hkjc.jessepractice.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.hkjc.jessepractice.IToastAIDLInterface;
import com.hkjc.jessepractice.RectPoint;


public class ToastService extends Service {
    public static final String TAG = "ToastService";
    public ToastService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return mToaster;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
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

    private final IToastAIDLInterface.Stub mToaster = new IToastAIDLInterface.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            //Do nothing
        }

        @Override
        public int getPID(String processTag) throws RemoteException {
            try {
                Thread.sleep(6*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Process.myPid();
        }


        @Override
        public RectPoint getRectPoint() throws RemoteException {
            RectPoint rectPoint = new RectPoint();
            rectPoint.setRect(2,3,4);
            return rectPoint;
        }
    };






}
