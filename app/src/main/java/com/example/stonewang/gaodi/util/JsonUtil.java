package com.example.stonewang.gaodi.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.stonewang.gaodi.NewsActivity;
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

public class JsonUtil extends AppCompatActivity{
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
                Log.d("xin新闻标题","标题--->"+News.getTitle());
                Log.d("xin新闻地址", "URL--->"+News.getUrl());
            }

            SendData(NewsData);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void SendData(LinkedList<GaoDiNews> NewsData){
        Intent intent = new Intent(JsonUtil.this, NewsActivity.class);
        intent.putExtra("data", "数据先行者");
        Log.d("News", "数据先行者");
        startActivity(intent);
    }


}
