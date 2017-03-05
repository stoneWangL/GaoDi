package com.example.stonewang.gaodi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.stonewang.gaodi.db.GaoDiNews;
import com.example.stonewang.gaodi.util.JsonUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stoneWang on 2017/2/25.
 */

public class NewsActivity extends AppCompatActivity {
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    /**
     * 选中的News
     */
    private GaoDiNews selectNews;
    /**
     * News列表
     */
    private List<GaoDiNews> gaoDiNewsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);

        backButton = (Button) findViewById(R.id.back_button);
        listView = (ListView) findViewById(R.id.list_view);

        //向服务器请求
        sendRequestWithOkHttp();
        //
        queryNews();

        adapter = new ArrayAdapter<>(NewsActivity.this, android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    /**
     * 查询选中的News，去数据库上查询
     */
    private void queryNews(){
        gaoDiNewsList = DataSupport.where("uniquekey = ?", String.valueOf(selectNews.getUniquekey())).find(GaoDiNews.class);
        if (gaoDiNewsList.size() > 0){
            dataList.clear();
            for (GaoDiNews gaoDiNews : gaoDiNewsList){
                dataList.add(gaoDiNews.getTitle());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
        }else{

        }
    }

    /**
     * 向服务器请求
     */
    private static void sendRequestWithOkHttp(){
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
                    JsonUtil jsonUtil = new JsonUtil();
                    jsonUtil.parseJson(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
