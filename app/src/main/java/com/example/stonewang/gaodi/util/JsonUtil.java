package com.example.stonewang.gaodi.util;

import android.text.TextUtils;
import com.example.stonewang.gaodi.db.GaoDiNews;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by stoneWang on 2017/3/4.
 * 1.开始解析对象
 * 2.开始解析字符串
 * 3.获取到一串数组的json对象
 * 4.并存入数据库
 */

public class JsonUtil{

    public static boolean parseJson(String jsonData){
//                Log.d("Json02", jsonData);
        if (!TextUtils.isEmpty(jsonData)){
            try{
                JSONObject jsonObject = new JSONObject(jsonData);
                String result = jsonObject.getString("result");
//                Log.d("Json03",result);

                JSONObject jsonObject2 = new JSONObject(result);
                String data = jsonObject2.getString("data");
//                Log.d("Json04",data);


                JSONArray allNews = new JSONArray(data);
                for (int i=0; i<allNews.length(); i++){
                    JSONObject NewsObject = allNews.getJSONObject(i);
//                    Log.d("Json05"+i, NewsObject.toString());
                    GaoDiNews News = new GaoDiNews();
                    News.setUniquekey(NewsObject.getString("uniquekey"));
                    News.setTitle(NewsObject.getString("title"));
                    News.setDate(NewsObject.getString("date"));
                    News.setCategory(NewsObject.getString("category"));
                    News.setAuthor_name(NewsObject.getString("author_name"));
                    News.setUrl(NewsObject.getString("url"));
                    News.setThumbnail_pic_s(NewsObject.getString("thumbnail_pic_s"));
                    News.save();
                }
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

}
