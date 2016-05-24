package com.hkjc.jessepractice.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hkjc.jessepractice.R;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class PracticeFragmentActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "Practice...Activity";
    private View v_tab1;
    private View v_tab2;
    private View v_tab3;
    private View v_tab4;
    private ContentFragment content1;
    private ContentFragment content2;
    private ContentFragment content3;
    private ContentFragment content4;
    private DownloadTask mDownloadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "PracticeFragmentActivity-onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_fragment_activity);
        v_tab1 = findViewById(R.id.tv_title1);
        v_tab2 = findViewById(R.id.tv_title2);
        v_tab3 = findViewById(R.id.tv_title3);
        v_tab4 = findViewById(R.id.tv_title4);
        v_tab1.setOnClickListener(this);
        v_tab2.setOnClickListener(this);
        v_tab3.setOnClickListener(this);
        v_tab4.setOnClickListener(this);
        mDownloadTask = new DownloadTask();
        setDefaultFragment();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mDownloadTask.execute();
        Log.d(TAG, "PracticeFragmentActivity-onResume()");
    }

    private void setDefaultFragment() {
        setFragment(1);
    }

    private void setFragment(int i) {
        ContentFragment content = null;
        try {
            Field field = getClass().getDeclaredField("content" + i);
            field.setAccessible(true);
            content = (ContentFragment) field.get(this);
            if (content == null) {
                Bundle bundle = new Bundle();
                bundle.putString(ContentFragment.KEY_DATA, "it's content" + i);
                content = ContentFragment.newInstance(bundle);
                field.set(this, content);
            }
        } catch (NoSuchFieldException e) {
            Toast.makeText(this, "no such field", Toast.LENGTH_SHORT);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Toast.makeText(this, "can't access this field", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
        if (content == null) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentF = fm.findFragmentByTag("content");
        if (content == currentF) {
            return;
        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_container, content, "content");
        transaction.addToBackStack("");
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice_fragment_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "PracticeFragmentActivity settings clicked!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings_2:
                Toast.makeText(this, "PracticeFragmentActivity settings2 clicked!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings_3:
                Toast.makeText(this, "PracticeFragmentActivity settings3 clicked!", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title1:
                setFragment(1);
                break;
            case R.id.tv_title2:
                setFragment(2);
                break;
            case R.id.tv_title3:
                setFragment(3);
                break;
            case R.id.tv_title4:
                setFragment(4);
                break;
        }
    }

    private class DownloadTask extends AsyncTask<Void,Integer,List<String>>{
        private ProgressDialog progressDialog ;
        boolean isTaskComplete = false;

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Arrays.asList("后台工作中.。。","后台工作中2。。。","后台工作中3。。。");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isTaskComplete = false;
            if(progressDialog == null){
                progressDialog = new ProgressDialog(PracticeFragmentActivity.this);
            }
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            isTaskComplete = true;
            progressDialog.dismiss();
        }
    }

}
