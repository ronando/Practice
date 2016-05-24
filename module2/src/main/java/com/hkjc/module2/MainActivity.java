package com.hkjc.module2;

import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hkjc.jessepractice.IToastAIDLInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static int id = 55;
    private IToastAIDLInterface mService;
    private ServiceConnection mServiceConnection;
    private PracticeContentObserver mContentObserver;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentObserver = new PracticeContentObserver(mHandler);
        new Thread(new TransferWorker()).start();
        Intent intent = getIntent();
        if(intent == null){
            return;
        }
        Bundle bundle = intent.getExtras();
        if(bundle == null){
            return;
        }
        Log.d("MainActivity", bundle.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            mServiceConnection = new ToastServiceConnection();
            testIPC(mServiceConnection);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        getContentResolver().registerContentObserver(Uri.parse("content://com.hkjc.jessepractice/table_name"),true,mContentObserver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
        getContentResolver().unregisterContentObserver(mContentObserver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void testIPC(ServiceConnection connection) throws RemoteException {
        Intent intent = new Intent();
        intent.setClassName("com.hkjc.jessepractice", "com.hkjc.jessepractice.service.ToastService");
        bindService(intent,connection,Context.BIND_AUTO_CREATE);
        if(mService !=null)
        {
            Log.d(TAG,"service process id :"+ mService.getPID(""));
        }else{
            Log.d(TAG,"service is null");
        }
    }

    public void testSharedPreference(){
        try {
            Context context = createPackageContext("com.hkjc.jessepractice",CONTEXT_IGNORE_SECURITY);
            SharedPreferences pref = context.getSharedPreferences("jesse_preference", Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE);
            Log.d(TAG, "read sharedpreference from app 1: name:" + pref.getString("name","error") + " password:" + pref.getLong("password",-1L));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private class ToastServiceConnection implements ServiceConnection {

        /**
         * Called when a connection to the Service has been established, with
         * the {@link IBinder} of the communication channel to the
         * Service.
         *
         * @param name    The concrete component name of the service that has
         *                been connected.
         * @param service The IBinder of the Service's communication channel,
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IToastAIDLInterface.Stub.asInterface(service);
            Log.d(TAG,"onServiceConnected");
        }

        /**
         * Called when a connection to the Service has been lost.  This typically
         * happens when the process hosting the service has crashed or been killed.
         * This does <em>not</em> remove the ServiceConnection itself -- this
         * binding to the service will remain active, and you will receive a call
         * to {@link #onServiceConnected} when the Service is next running.
         *
         * @param name The concrete component name of the service whose
         *             connection has been lost.
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            Log.d(TAG,"onServiceDisconnected");
        }
    }

    private class TransferWorker implements  Runnable{
        @Override
        public void run(){
            /**********************
             * test content provider
             */
            Random random = new Random();
            id = random.nextInt(2500);
            for(int i = 0,n=5; i<n ; i++){
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ContentValues values = new ContentValues();
                values.put("id",++id);
                values.put("name","jesse");
                values.put("description","english name");
                values.put("date", "2016-01-01");
                values.put("sex", "male");
                Uri jesseItem = getContentResolver().insert(Uri.parse("content://com.hkjc.jessepractice/table_name"),values);
                Log.d(TAG, "insert item: " + jesseItem);

                getContentResolver().delete(Uri.parse("content://com.hkjc.jessepractice/table_name"),"name=?",new String[]{"jesse"});

            }
            testSharedPreference();

            /**********************
             * 跨 app 访问数据
             */
//            InputStream is = null;
//            OutputStream os = null;
//            try {
//                Context context = createPackageContext("com.hkjc.jessepractice",Context.CONTEXT_IGNORE_SECURITY);
//
//                File file = new File("data/data/com.hkjc.jessepractice/jessesrandom");
//                if(!file.exists()){
//                        file.createNewFile();
//                }
//                is = new FileInputStream(file);
//                InputStreamReader reader = new InputStreamReader(is);
//                char[] s = new char[1024];
//                reader.read(s);
//                String result = s.toString();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                if(os != null){
//                    try {
//                        os.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(is != null)
//                {
//                    try {
//                        is.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        }
    }


    private class PracticeContentObserver extends ContentObserver{

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public PracticeContentObserver(Handler handler) {
            super(handler);
        }


        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Log.d(TAG,"database changed Uri:"+uri);
        }
    }
}
