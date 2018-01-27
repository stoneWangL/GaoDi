package com.example.stonewang.gaodi.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stoneWang on 2018/1/27.
 */

public class CommonFunction {

    /**
     *
     */
    public static void sendHttpRequest(String URL, String func){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问服务器地址
                            .url("URL")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将服务器返回得到字符串传入处理函数
                    JsonUtil jsonUtil = new JsonUtil();
                    jsonUtil.parseJsonJunshi(responseData);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
