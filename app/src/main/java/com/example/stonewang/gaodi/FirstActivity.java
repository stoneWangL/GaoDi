package com.example.stonewang.gaodi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.stonewang.gaodi.db.Comment;
import com.example.stonewang.gaodi.db.GuojiNews;
import com.example.stonewang.gaodi.db.JunshiNews;
import com.example.stonewang.gaodi.util.JsonUtil;

import org.litepal.crud.DataSupport;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by StoneWang on 2018/2/3.
 */

public class FirstActivity extends AppCompatActivity{
    public int firstTimes=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        delete_Message();
        delete_DataBase();
        //请求新闻数据存入本地数据库
        init();
        //延迟一段时间后跳转到另一个界面
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent intent = new Intent (FirstActivity.this,LoginActivity.class);
                startActivity(intent);//跳转界面
                FirstActivity.this.finish();//关闭此界面
            }
        }, 1000);

    }
    public void delete_Message(){
        SharedPreferences.Editor editor = getSharedPreferences("User",MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
    //清空之前的数据库
    public void delete_DataBase(){
        DataSupport.deleteAll(JunshiNews.class);
        DataSupport.deleteAll(GuojiNews.class);
        DataSupport.deleteAll(Comment.class);
    }

    /**
     * 初始化数据库（新闻、评论、本地数据库）
     */
    private void init(){
        /**
         * 京东云API_Junshi的返回结果
         * http://114.67.243.127/index.php/API/Api/junshiTest/number/1
         */
        if (DataSupport.findAll(JunshiNews.class).size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://114.67.243.127/index.php/API/Api/junshiTest/number/"+firstTimes)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        //将API返回的json数据传递给处理函数
                        JsonUtil jsonUtil = new JsonUtil();
                        jsonUtil.parseJsonJunshi(responseData);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        int num = 0;
        num = DataSupport.findAll(JunshiNews.class).size();
        Log.d("stone00","JunshiNews->size的大小是"+num);
        if (DataSupport.findAll(JunshiNews.class).size()==0 | DataSupport.findAll(JunshiNews.class).size() >10){
            num = firstTimes+1;
        }else{
            num = (DataSupport.findAll(JunshiNews.class).size()/10)+1;
        }
        saveJunshiNum(num);//记录number=2,即已經請求了1頁，下次請求2頁

        /**
         * 国际
         * 京东云API->Guoji的返回结果
         */
        if (DataSupport.findAll(GuojiNews.class).size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://114.67.243.127/index.php/API/Api/guojiTest/number/"+firstTimes)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        //将API返回的json数据传递给处理函数
                        JsonUtil jsonUtil = new JsonUtil();
                        jsonUtil.parseJsonGuoji(responseData);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        int guojinum = 0;
        guojinum = DataSupport.findAll(GuojiNews.class).size();
        Log.d("stone00","GuojiNews->size的大小是"+guojinum);
        if (guojinum==0 | guojinum >8){
            guojinum = firstTimes+1;
        }else{
            guojinum = (guojinum/10)+1;
        }
        saveGuojiNum(guojinum);//记录number=2,即已經請求了1頁，下次請求2頁

        /**
         * 评论
         * 京东云API->Comment的返回结果
         */
        if(DataSupport.findAll(Comment.class).size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://114.67.243.127/index.php/API/Api/findAllComment")
                                .build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        //将服务器返回得到字符串传入处理函数

                        if (responseData.equals("0")){
                            Log.d("stone006","返回数据为空");
                        }else{
                            DataSupport.deleteAll(Comment.class);//先清空Comment本地数据库
                            Log.d("stone006",responseData);
                            JsonUtil jsonUtil = new JsonUtil();
                            jsonUtil.parseJsonComment(responseData);//将返回的数据按UTF-8编码后，解析存入本地数据库Comment
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 用于创建保存Junshi页面的请求的，页面数
     */
    public void saveJunshiNum(int num){
        SharedPreferences.Editor editor = getSharedPreferences("numPage",MODE_PRIVATE).edit();
        editor.putInt("num",num);
        editor.apply();
    }
    /**
     * 用于创建保存Guoji页面的请求的，页面数
     */
    public void saveGuojiNum(int guojinum){
        SharedPreferences.Editor editor = getSharedPreferences("numPage",MODE_PRIVATE).edit();
        editor.putInt("guojinum",guojinum);
        editor.apply();
    }


}
