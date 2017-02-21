package com.example.stonewang.gaodi.util;

import android.text.TextUtils;

import com.example.stonewang.gaodi.db.JsonBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stoneWang on 2017/2/21.
 */

public class Utility {

    /**
     * 解析和处理服务器返回的军事数据
     */
    public static boolean handleNewsResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allNews = new JSONArray(response);
                for (int i=0; i<allNews.length(); i++){
                    JSONObject newsObject = allNews.getJSONObject(i);
                    JsonBean jsonBean = new JsonBean();
                    jsonBean.setReason(newsObject.getString("reason"));
                    jsonBean.setError_code(newsObject.getInt("error_code"));
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }


}
