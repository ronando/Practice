package com.hkjc.jessepractice.UI;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.hkjc.jessepractice.BitmapUtil.BitmapUtil;
import com.hkjc.jessepractice.MyContentProvider;
import com.hkjc.jessepractice.R;
import com.hkjc.jessepractice.RectPoint;
import com.hkjc.jessepractice.service.PracticeHandlerThread;
import com.hkjc.jessepractice.xml.AAA;
import com.hkjc.jessepractice.xml.AAAParser;
import com.hkjc.jessepractice.xml.JsonParser;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import android.R.*;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private ImageView mImageView;
    private Button mButton;
    private Button mButton2;
    private AnimationDrawable animationDrawable;

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.iv_bigimg);
        mButton = (Button) findViewById(R.id.b_button);
        mButton2 = (Button) findViewById(R.id.b_button2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.setClassName("com.hkjc.module2", "com.hkjc.module2.GuardService");
//
////                Bundle bundle = new Bundle();
////                bundle.putString("key1string","value1");
////                bundle.putBoolean("key2bool", true);
////                bundle.putInt("key3int", 250);
////                intent.putExtras(bundle);
////                intent.setData(Uri.parse(""));
////                startActivity(intent);
//                startService(intent);
//                intent.setClass(getApplicationContext(), PracticeFragmentActivity.class);
                intent.setClass(getApplication(),DrawerActivity.class);
                startActivity(intent);

            }
        });

        PracticeHandlerThread downloadThread = new PracticeHandlerThread("Downloader thread");
        downloadThread.start();
        final Handler handler = new Handler(downloadThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"current thread: "+ Thread.currentThread().getName() + " msg.what:"+msg.what);
            }
        };

        mButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("jesse_practice", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("key_aaa", "value_aaa");
                editor.commit();
                testSdcard();
                float i = 0;
                while (i < 99999999) {
                    i += 0.00001;
                }
                handler.sendEmptyMessage(999);
                handler.sendEmptyMessage(888);
            }
        });


    }

//    private void initDrawerAndToolbar(){
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerToggle =  new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.action_settings,R.string.app_name){
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public boolean onOptionsItemSelected(MenuItem item) {
//                return super.onOptionsItemSelected(item);
//            }
//
//            @Override
//            public void onConfigurationChanged(Configuration newConfig) {
//                super.onConfigurationChanged(newConfig);
//            }
//        };
//
//        mToolbar.setTitle("it's toolbar title");
//        mToolbar.setBackgroundColor(Color.BLACK);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//        mDrawerToggle.syncState();
//
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
//    }


    private void testAnimation(){
        //test animation drawable case 1:
        mImageView.setBackgroundResource(R.drawable.ibu_splash);
        animationDrawable = (AnimationDrawable) mImageView.getBackground();


//        test animation drawable case 2:
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.ibu_splash);
        mImageView.setBackground(animationDrawable);

//        test view animation
        AnimationSet animationSet = new AnimationSet(false);

        //scale
        Animation scale = new ScaleAnimation(1,2,1,2,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(3000);
        animationSet.addAnimation(scale);

        //alpha
        Animation alpha = new AlphaAnimation(1.0f,0.0f);
        alpha.setDuration(10*1000);
        animationSet.addAnimation(alpha);
        animationSet.setRepeatCount(3);
        animationSet.setFillAfter(true);
        mButton2.startAnimation(animationSet);
    }

    private void testXMLObjectAnimator(){
        Animator animator = AnimatorInflater.loadAnimator(this,R.animator.change_btn);
        animator.setTarget(mButton);
        animator.start();
    }

    private void testObjectAnimatorAlpha(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mButton2,"alpha",1f,0f);
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(3000);
        animator.start();
    }

    private void testObjectAnimatorTranslation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mButton,"translationX",1f,800f);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(14000);
        animator.start();
    }

    private void testDBInsert(){
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MyContentProvider.ID,1010101);
        values.put(MyContentProvider.NAME, "jjjjjjjj");
        values.put("description", "english name");
        values.put("date", "2016-01-01");
        values.put("sex", "male");
        Uri index = cr.insert(MyContentProvider.CONTENT_URI, values);
    }

    private void testAppMemory(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        Log.d("MainActivity", "testHeapSize" + activityManager.getMemoryClass());
        Log.d("MainActivity", "testLargeHeapSize" + activityManager.getLargeMemoryClass());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            testBitmapUtil();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        testSdcard();
        Thread parserThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    testHttp();
                    testSharedPreference();
//                    testXMLParser();
//                    testJsonParser();
//                    testSdcard();
                    Random random = new Random();
                    int[] array1 = new int[5000], array2, array3;
                    for (int i = 0, n = array1.length; i < n; i++) {
                        array1[i] = random.nextInt(50);
                    }
                    array2 = array1.clone();
                    array3 = array1.clone();
                    Log.d(TAG, "array1 : " + arrayToString(array1));
                    Log.d(TAG, "array2 : " + arrayToString(array2));
                    Log.d(TAG, "array3 : " + arrayToString(array3));
                    long start = System.currentTimeMillis();
                    sortByBubble(array1);
                    Log.d(TAG, "sort by bubble " + (System.currentTimeMillis() - start) + "ms " + arrayToString(array1));
                    start = System.currentTimeMillis();
                    sortByQuickSort(array2, 0, array2.length - 1);
                    Log.d(TAG, "sort by quick sort " + (System.currentTimeMillis() - start) + "ms " + arrayToString(array2));
                    start = System.currentTimeMillis();
                    sortByChooseSort(array3);
                    Log.d(TAG, "sort by choose sort " + (System.currentTimeMillis() - start) + "ms " + arrayToString(array3));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        parserThread.start();
    }

    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = array.length; i < n; i++) {
            sb.append(array[i]);
            sb.append(" ");
        }
        return sb.toString();
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

    private void testService(){

    }

    private void testSdcard() {
        File file = new File(Environment.getExternalStorageDirectory(), "jesse_rect");
        try {
            if (!file.exists()) {
                file.createNewFile();
                RectPoint rect = new RectPoint();
                rect.setRect(10, 20, 30);
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
                os.writeObject(rect);
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    public void testHttp(){
        URL url = null;
        try {
            url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            DataInputStream dis = new DataInputStream(inputStream);
            byte[] buffer = new byte[500];
            dis.read(buffer);
            String s = new String(buffer);
            Log.d(TAG, "http:" + s);
            dis.close();
            inputStream.close();
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void testSharedPreference() {
        SharedPreferences.Editor editor = getSharedPreferences("jesse_preference", Context.MODE_WORLD_READABLE).edit();
        editor.clear();
        editor.putString("name", "jesse");
        editor.putLong("password", 1000L);
        editor.commit();
    }

    private void testXMLParser() throws Exception {
        AAAParser aaaParser = new AAAParser();
        aaaParser.exeuteParser(getResources().getAssets().open("AAA.xml"));
        AAA aaa = aaaParser.getData();
        Log.d("MainActivity", "testXMLParser()" + aaa);
    }

    private void testJsonParser() throws Exception {
        JsonParser aaaParser = new JsonParser();
        String object = "{\"id\":28,\"name\":\"奶茶\",\"content\":\"babababa\"}";
        String objects = "[{\"id\":28,\"name\":\"奶茶\",\"content\":\"babababa奋斗奋斗\"},{\"id\":29,\"name\":\"奶茶果冻\",\"content\":\"babababa奋斗奋斗\"},{\"id\":30,\"name\":\"奶茶朱古力\",\"content\":\"babababa热热\"}]";
        aaaParser.parseJson(object);
        aaaParser.parseJsonArray(objects);
    }

    private void testBitmapUtil() throws IOException {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bigpic);
        mImageView.setImageBitmap(bitmap);
        Log.d("MainAcvity", "bitmap size (original):" + bitmap.getRowBytes());
//        Bitmap compressedBitmap = BitmapUtil.compressBitmap(bitmap,1024*20);
//        BitmapUtil.saveBitmapToDisk(compressedBitmap,path,"compressed");
//        Log.d("MainAcvity", "bitmap size (compressed):" + compressedBitmap.getByteCount());
        Bitmap scaledBM = BitmapUtil.sampleSizeBitmap(bitmap, 100, 100);
        BitmapUtil.saveBitmapToDisk(bitmap, path, "scaled");
        Log.d("MainAcvity", "bitmap size (scaled):" + bitmap.getByteCount());
        mImageView.setImageBitmap(bitmap);
    }


    private void sortByBubble(int[] array) {
        boolean isArrayChanged = false;
        int i = array.length, j, temp;
        while (i-- > 0) {
            isArrayChanged = false;
            for (j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    isArrayChanged = true;
                }
            }
            if (!isArrayChanged) return;
        }
    }

    private void sortByQuickSort(int[] array, int start, int end) {
        if (start >= end) return;
        int low = start, high = end, pivoKey = array[start];
        while (low < high) {
            while (array[high] >= pivoKey && low < high) {
                high--;
            }
            array[low] = array[high];
            while (array[low] <= pivoKey && low < high) {
                low++;
            }
            array[high] = array[low];
        }
        array[low] = pivoKey;
        if (low > start) {
            sortByQuickSort(array, start, low - 1);
        }
        if (high < end) {
            sortByQuickSort(array, low + 1, end);
        }
    }

    private void sortByChooseSort(int[] array) {
        int index = 0, min;
        for (int j = 0, n = array.length; j < n; j++) {
            index = j;
            min = array[j];
            for (int i = j; i < n; i++) {
                if (min > array[i]) {
                    min = array[i];
                    index = i;
                }
            }
            array[index] = array[j];
            array[j] = min;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(animationDrawable != null){
                animationDrawable.start();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
