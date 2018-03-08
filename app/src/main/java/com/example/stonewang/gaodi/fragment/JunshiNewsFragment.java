package com.example.stonewang.gaodi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.stonewang.gaodi.MyItemDecoration;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.JunshiItemAdapter;
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
 * 军事页面的fragment
 */

public class JunshiNewsFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private JunshiItemAdapter adapter;
    private List<JunshiNews> JunshiNewsList=new ArrayList<>(), Test=new ArrayList<>(), Temp=new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        JunshiNewsList.clear();
        JunshiNewsList = DataSupport.order("id desc").find(JunshiNews.class);

        Message message = new Message();
        if (JunshiNewsList.size()==0){
            message.what = 1;
        }else {
            message.what = 2;
        }
        backgroundNext.sendMessage(message);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("stone00","声明周期->onCreate");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("stone00","声明周期->onCreateView");
        View v =  inflater.inflate(R.layout.choose_area, container, false);
//        init();//初始化

        stoneNum();

        //控件初始化，填充内容
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager (getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());//添加装饰类,加横线
        adapter = new JunshiItemAdapter(JunshiNewsList);
        recyclerView.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);


        getNum();
        return v;
    }

    private Handler backgroundNext = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.choose_area_layout);
            switch (msg.what){
                case 1:
                    linearLayout.setBackgroundResource(R.drawable.background03);
//                    imageView.setBackgroundResource(R.drawable.background01);
                    break;
                case 2:
//                    imageView.setBackgroundResource(R.drawable.background02);
                    linearLayout.setBackgroundResource(R.drawable.background02);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        Log.d("stone00","声明周期->onResume");
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshJunshiNews();//更新新闻列表
            }
        });
        stoneNum();
    }

    /**
     * 更新新闻列表
     */
    private void refreshJunshiNews() {

//        sendRequestWithOkHttp();//向服务器发送请求，并插入本地数据库
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (getActivity() == null)
                    return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int num = getNum();
                        if(num > 10){
                            Toast.makeText(getContext(), "暂时没有更新", Toast.LENGTH_SHORT).show();
                        }else{
                            sendRequestWithOkHttp(num);
                            try{
                                sleep(1000);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            Temp.clear();JunshiNewsList.clear();
                            Temp = DataSupport.order("id desc").find(JunshiNews.class);
                            for (JunshiNews all:Temp){
                                JunshiNewsList.add(all);
                            }
                            try{
                                sleep(500);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }
                        swipeRefresh.setRefreshing(false);//耗时操作结束


//                        stoneLog();
                        stoneNum();
                    }
                });
            }
        }).start();

    }

    /**
     * 向服务器请求
     */
    private void sendRequestWithOkHttp(int num){
        final String urlString = "http://114.67.243.127/index.php/API/Api/junshiTest/number/"+num;
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
                    jsonUtil.parseJsonJunshi(responseData);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        num++;
        saveNum(num);
    }

    /**
     * 从文件中取出number
     */
    public int getNum(){
        SharedPreferences pref = getContext().getSharedPreferences("numPage", MODE_PRIVATE);
        int num = pref.getInt("num",0);
        Log.d("stone0012","getNum="+num);
        return num;
    }

    /**
     * 用于创建保存文件
     */
    public void saveNum(int num){
        Log.d("stone0012","saveNum="+num);
        SharedPreferences.Editor editor = getContext().getSharedPreferences("numPage",MODE_PRIVATE).edit();
        editor.putInt("num",num);
        editor.apply();
    }

    public void stoneLog(){
        Test = DataSupport.order("id desc").find(JunshiNews.class);
        for(JunshiNews Tests:Test){
            Log.d("stone002"," JunshiNews id :"+Tests.getId());
            Log.d("stone002","JunshiNews newsid:"+Tests.getNewsid());
            Log.d("stone002"," JunshiNews title :"+Tests.getTitle());
        }
    }

    public void stoneNum(){
        Test.clear();
        Test = DataSupport.findAll(JunshiNews.class);
        Log.d("stone001","军事Fragment初始化size="+Test.size());
    }



}
