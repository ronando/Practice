package com.hkjc.jessepractice.xml;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jesse on 6/11/15.
 */
public class JsonParser {




    //{"id":28,"name":"奶茶","content":"babababa"}
    public Object parseJson(String json){
        JSONObject object = null;
        try {
            object = new JSONObject(json);
            Log.d("JsonParser", "id:" + object.get("id") +object.getString("name") + object.getString("content"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    //[{"id":28,"name":"奶茶","content":"babababa奋斗奋斗"},{"id":29,"name":"奶茶果冻","content":"babababa奋斗奋斗"},{"id":30,"name":"奶茶朱古力","content":"babababa热热"}]
    public Object parseJsonArray(String json){
        JSONArray array = null;
        try {
            array = new JSONArray(json);
            JSONObject object;
            for(int i =0,n = array.length(); i<n; i++){
                object = array.getJSONObject(i);
                Log.d("JsonParser", "id:" + object.get("id") +object.getString("name") + object.getString("content"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

}
