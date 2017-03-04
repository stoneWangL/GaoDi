package com.example.stonewang.gaodi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stonewang.gaodi.gson.GaoDiNews;
import com.example.stonewang.gaodi.util.HttpUtil;
import com.example.stonewang.gaodi.util.JsonUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stoneWang on 2017/2/25.
 */

public class NewsActivity extends AppCompatActivity {
    private ScrollView newsLayout;
    private TextView newsTitle;
    private TextView titleUpDataTime;
    private TextView newsContent;
    private String JsonStringOne;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //初始化控件
        newsLayout = (ScrollView) findViewById(R.id.news_layout);
        newsTitle = (TextView) findViewById(R.id.news_title);
        titleUpDataTime = (TextView) findViewById(R.id.title_update_time);
        newsContent = (TextView) findViewById(R.id.news_content);
        Button queryNews = (Button) findViewById(R.id.query_news);
        queryNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
                newsContent.setText(JsonStringOne);
            }
        });
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问服务器地址
                            .url("http://v.juhe.cn/toutiao/index?type=junshi&key=3425b3b2cd4d3227f7455377f6276bab")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将服务器返回得到字符串传入处理函数
                    parseJSONWithJSONObject(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        JsonStringOne = jsonData;

        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.parseJson(jsonData);

    }
}
