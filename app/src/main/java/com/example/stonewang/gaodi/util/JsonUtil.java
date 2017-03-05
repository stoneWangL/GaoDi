package com.example.stonewang.gaodi.util;

import android.util.Log;
import com.example.stonewang.gaodi.gson.GaoDiNews;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by stoneWang on 2017/3/4.
 * 1.开始解析对象
 * 2.开始解析字符串
 */

public class JsonUtil {
    public void parseJson(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String reason = jsonObject.getString("reason");
            String result = jsonObject.getString("result");
            Log.d("JsonOneStep",reason);
            Log.d("JsonOneStep",result);
            parseStepTwo(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void parseStepTwo(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String stat = jsonObject.getString("stat");
            String data = jsonObject.getString("data");
            Log.d("JsonTwoStep",stat);
            Log.d("JsonTwoStep",data);
            parseStepThree(data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void parseStepThree(String jsonData){
        try{
            Type listType = new TypeToken<LinkedList<GaoDiNews>>(){}.getType();
            Gson gson = new Gson();
            LinkedList<GaoDiNews> NewsData = gson.fromJson(jsonData, listType);
            for (Iterator iterator = NewsData.iterator(); iterator.hasNext(); ){
                GaoDiNews News = (GaoDiNews) iterator.next();
                Log.d("新闻标题","标题--->"+News.getTitle());
                Log.d("新闻地址", "URL--->"+News.getUrl());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
