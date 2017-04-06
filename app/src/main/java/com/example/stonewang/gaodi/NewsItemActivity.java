package com.example.stonewang.gaodi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.stonewang.gaodi.adapter.NewsItemAdapter;
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

public class NewsItemActivity extends AppCompatActivity {
    public static NewsItemActivity mactivity;
    private Button backButton;
    private SwipeRefreshLayout swipeRefresh;
    private NewsItemAdapter adapter;

    private List<GaoDiNews> gaoDiNewsList=new ArrayList<>(), All=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);
        mactivity=this;

        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        gaoDiNewsList.clear();
        All.clear();
        All = DataSupport.findAll(GaoDiNews.class);
        int i = All.size();String j = ""+i;Log.d("num1", "size="+j);//打印信息
        if (All.size()>0){
            //有缓存
        }else{
            //向服务器请求
            sendRequestWithOkHttp();
            All = DataSupport.findAll(GaoDiNews.class);
        }

        for (GaoDiNews all:All){
            gaoDiNewsList.add(all);
        }



        //控件初始化，填充内容
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);
        //添加装饰类
        recyclerView.addItemDecoration(new MyItemDecoration());
        adapter = new NewsItemAdapter(gaoDiNewsList);

        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                refreshGaoDiNews();
                DataSupport.deleteAll(GaoDiNews.class);//清空本地数据库缓存
                sendRequestWithOkHttp();//向服务器发送请求，并插入本地数据库
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(4000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gaoDiNewsList.clear();
                                All.clear();
                                All = DataSupport.findAll(GaoDiNews.class);

                                int ii = All.size();String jj = ""+ii;Log.d("num2", "size="+jj);//打印信息
                                for (GaoDiNews all:All){
                                    gaoDiNewsList.add(all);
                                }

                                adapter.notifyDataSetChanged();
                                swipeRefresh.setRefreshing(false);//耗时操作结束

                                Toast.makeText(NewsItemActivity.this, "新闻已更新", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

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

    private void refreshGaoDiNews(){

    }

    /**
     * 刷新界面
     */
    private void refresh(){
        gaoDiNewsList = DataSupport.findAll(GaoDiNews.class);
        adapter = new NewsItemAdapter(gaoDiNewsList);

    }
}
