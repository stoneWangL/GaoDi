package com.example.stonewang.gaodi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stonewang.gaodi.MyItemDecoration;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.JunshiItemAdapter;
import com.example.stonewang.gaodi.db.GaoDiNews;
import com.example.stonewang.gaodi.db.JunshiNews;
import com.example.stonewang.gaodi.util.JsonUtil;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stoneWang on 2017/4/7.
 * 军事页面的fragment
 */

public class JunshiItemFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private JunshiItemAdapter adapter;
    private List<JunshiNews> JunshiNewsList=new ArrayList<>(), All=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.choose_area, container, false);

        init();//初始化

        //控件初始化，填充内容
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager (getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());//添加装饰类,加横线
        adapter = new JunshiItemAdapter(JunshiNewsList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshGaoDiNews();//更新新闻列表
            }
        });


        return v;
    }
    /**
     * 初始化
     */
    private void init(){
        JunshiNewsList.clear();
        All.clear();
        All = DataSupport.findAll(JunshiNews.class);
        int i = All.size();
        Log.d("num83","军事Fragment初始化size="+i);
        if (All.size()>0){
            //有缓存
        }else{
            //向服务器请求
            sendRequestWithOkHttp();
            All = DataSupport.findAll(JunshiNews.class);
        }

        for (JunshiNews all:All){
            JunshiNewsList.add(all);
        }
    }

    /**
     * 更新新闻列表
     */
    private void refreshGaoDiNews() {
//        i++;
        DataSupport.deleteAll(GaoDiNews.class);//清空本地数据库缓存
        sendRequestWithOkHttp();//向服务器发送请求，并插入本地数据库
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (getActivity() == null)
                    return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JunshiNewsList.clear();
                        All.clear();
                        All = DataSupport.findAll(JunshiNews.class);

//                        int ii = All.size();String jj = ""+ii;Log.d("num2", "size="+jj);//打印信息
                        for (JunshiNews all:All){
                            JunshiNewsList.add(all);
                        }

                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);//耗时操作结束

//                        Toast.makeText(getActivity(), "新闻已更新", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
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
                            .url("http://114.67.243.127/index.php/API/Api/junshi")
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

    /**
     * 从文件中取出number
     */

}
