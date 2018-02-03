package com.example.stonewang.gaodi.fragment;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.stonewang.gaodi.MyItemDecoration;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.GuojiItemAdapter;
import com.example.stonewang.gaodi.db.GuojiNews;
import com.example.stonewang.gaodi.db.JunshiNews;
import com.example.stonewang.gaodi.util.JsonUtil;

import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Thread.sleep;

/**
 * Created by stoneWang on 2017/4/7.
 */

public class GuojiNewsFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private GuojiItemAdapter adapter;
    private List<GuojiNews> guojiNewsList =new ArrayList<>(), Test=new ArrayList<>(), Temp=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.choose_area, container, false);

//        init();//初始化
        guojiNewsList.clear();
        guojiNewsList = DataSupport.order("id desc").find(GuojiNews.class);

        //控件初始化，填充内容
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager (getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());//添加装饰类,加横线
        adapter = new GuojiItemAdapter(guojiNewsList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshGuojiNews();//更新新闻列表
            }
        });
    }

    /**
     * 更新新闻列表
     */
    private void refreshGuojiNews() {
//        sendRequestWithOkHttp();//向服务器发送请求，并插入本地数据库
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
                        int guojiNum = getGuojiNum();
                        if(guojiNum > 8){
                            Toast.makeText(getContext(), "暂时没有更新", Toast.LENGTH_SHORT).show();
                        }else{
                            sendRequestWithOkHttp(guojiNum);
                            try{
                                sleep(1000);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            Temp.clear();guojiNewsList.clear();
                            Temp = DataSupport.order("id desc").find(GuojiNews.class);
                            for (GuojiNews all:Temp){
                                guojiNewsList.add(all);
                            }
                            try{
                                sleep(500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }
                        swipeRefresh.setRefreshing(false);//耗时操作结束
                    }
                });
            }
        }).start();
    }

    /**
     * 向服务器请求
     * 并创建本地数据库
     */
    private void sendRequestWithOkHttp(int num){
        final String urlString = "http://114.67.243.127/index.php/API/Api/guojiTest/number/"+num;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问服务器地址
                            .url(urlString)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将服务器返回得到字符串传入处理函数
                    JsonUtil jsonUtil = new JsonUtil();
                    jsonUtil.parseJsonGuoji(responseData);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        num++;
        saveGuojiNum(num);
    }
    /**
     * 作用：将数据储存到SharedPreferences中
     * 数据：国际页面已经请求的->页面数
     * 补充：numPage->键值对文件名
     */
    public void saveGuojiNum(int guojinum){
        Log.d("stone0012","guojinum->saveNum="+guojinum);
        SharedPreferences.Editor editor = getContext().getSharedPreferences("numPage",MODE_PRIVATE).edit();
        editor.putInt("guojinum",guojinum);
        editor.apply();
    }

    /**
     * 作用：从SharedPreferences中读取数据
     * 数据：国际页面应该请求的->页面数
     * 补充：numPage->键值对文件名
     */
    public int getGuojiNum(){
        SharedPreferences pref = getContext().getSharedPreferences("numPage", MODE_PRIVATE);
        int guojinum = pref.getInt("guojinum",0);
        Log.d("stone0012","guojinum->getNum="+guojinum);
        return guojinum;
    }

}