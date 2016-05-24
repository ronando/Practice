package com.hkjc.jessepractice.UI;

import android.os.*;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hkjc.jessepractice.R;


public class Main2Activity extends FragmentActivity {
    public static final String TAG = "Main2Activity";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private View im_indicator;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private View fl_indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_copy);
        im_indicator = findViewById(R.id.iv_indicator);
        fl_indicator = findViewById(R.id.fl_indicator);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics matrix = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(matrix);
        final int screenWidth = matrix.widthPixels;
        final int offsetUnit = (screenWidth/3 - im_indicator.getWidth())/2;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                int offset = (int) (offsetUnit + (screenWidth/3)*(position + positionOffset));
//                Log.d(TAG, "position:" + position + " positionOffset:" + positionOffset + " positionOffsetPixels:" + positionOffsetPixels + " offset:"+offset);
//                lp.setMargins(offset,0,0,0);
//                im_indicator.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "offset:" + (offsetUnit + position*screenWidth/3));
                fl_indicator.setPadding(offsetUnit + position * screenWidth/3,0,0,0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            PlaceholderFragment fragment = PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    fragment.setBackground(R.color.red);
                    break;
                case 1:
                    fragment.setBackground(R.color.green);
                    break;
                case 2:
                    fragment.setBackground(R.color.black);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }


    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private int mBackground;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.d(TAG,"PlaceholderFragment-onCreateView()");
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            rootView.setBackgroundColor(mBackground);
            return rootView;
        }

        public void setBackground(int colorId){
            Log.d(TAG,"PlaceholderFragment-setBackground()");
            mBackground = colorId;
        }
    }

}
