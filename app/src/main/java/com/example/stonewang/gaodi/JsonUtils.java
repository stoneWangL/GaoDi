package com.example.stonewang.gaodi;

import android.util.JsonReader;
import android.util.Log;

import java.io.StringReader;

/**
 * Created by stoneWang on 2017/3/3.
 */

public class JsonUtils {
    /**
     * 1.开始解析数组
     * 2.开始解析对象
     * 3.解析键值对
     * 4.解析键值对
     * 5.解析对象结束
     * 6.开始解析对象
     * 7.解析键值对
     * 8.解析键值对
     * 9.解析对象结束
     * 10.结束解析数组
     * @param jsonData
     */
    public void parseJson(String jsonData){
        try{
            //如果需要解析JSON数据，首先要生成一个JsonReader对象
            JsonReader reader = new JsonReader(new StringReader(jsonData));
            reader.beginArray();
            while(reader.hasNext()){
                reader.beginObject();
                while(reader.hasNext()){//hasNext()判断有没键值对
                    String tagName = reader.nextName();
                    if (tagName.equals("name")){
                        Log.d("TestGson", "name---->"+reader.nextString());
                    }
                    else if (tagName.equals("age")){
                        Log.d("TestGson","age--->"+reader.nextInt());
                    }
                }
                reader.endObject();
            }
            reader.endArray();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
