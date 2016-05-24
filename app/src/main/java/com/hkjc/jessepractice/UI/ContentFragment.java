package com.hkjc.jessepractice.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hkjc.jessepractice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jesse on 3/2/16.
 */
public class ContentFragment extends Fragment {

    public static final String TAG = "ContentFragment";
    public static final String KEY_DATA = "key_data";
    private TextView tv_content;
    private ListView lv_content;
    private SimpleAdapter mAdapter;

    public static ContentFragment newInstance(Bundle bundle){
        ContentFragment f = new ContentFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView()");
        View root =  getActivity().getLayoutInflater().inflate(R.layout.content_fragment, container, false);
        tv_content = (TextView) root.findViewById(R.id.tv_content);
        lv_content = (ListView) root.findViewById(R.id.lv_content);
        List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
        Map<String,String> data;
        for(int i =0,n=20; i<n; i++){
            data = new HashMap<String,String>();
            data.put("title","title"+i);
            data.put("content","content"+i);
            datas.add(data);
        }
        mAdapter = new SimpleAdapter(getActivity(),datas,android.R.layout.simple_list_item_2,new String[]{"title","content"},new int[]{android.R.id.text1,android.R.id.text2});
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        Bundle data = getArguments();
        if(data != null){
            tv_content.setText(data.getString(KEY_DATA,"no information"));
        }
        lv_content.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }


    //test options menu  TODO:
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_practive_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(getActivity(),"content fragment settings clicked!",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings_2:
                Toast.makeText(getActivity(),"content fragment settings2 clicked!",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings_3:
                Toast.makeText(getActivity(),"content fragment settings3 clicked!",Toast.LENGTH_LONG).show();
                break;
            default:
               break;
        }
        return true;
    }
}
