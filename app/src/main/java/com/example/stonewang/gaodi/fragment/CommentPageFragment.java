package com.example.stonewang.gaodi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stonewang.gaodi.JunshiNewsActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.CommentAdapter;
import com.example.stonewang.gaodi.db.Comment;
import com.example.stonewang.gaodi.util.JsonUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Thread.sleep;

/**
 * Created by Lenovo on 2018/2/5.
 */

public class CommentPageFragment extends Fragment {
    private List<Comment> commentList = new ArrayList<>(), temp = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tab2, container, false);



        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);
        RequestComment();


        return v;
    }

    private Handler GetComment = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    commentList = temp;

                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 请求评论数据
     */
    private void RequestComment(){
        Intent intent = getActivity().getIntent();
        int newsid = intent.getIntExtra("NewsId",0);
        String news = intent.getStringExtra("News");

        //http://114.67.243.127/index.php/API/Api/findComment/news/junshi/newsid/5420
        final String url = "http://114.67.243.127/index.php/API/Api/findComment/news/"+news+"/newsid/"+newsid;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问服务器地址
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将服务器返回得到字符串传入处理函数

                    if (responseData.equals("0")){
                        Log.d("stone006","返回数据为空");
                    }else{
                        Log.d("stone006",responseData);
                        JsonUtil jsonUtil = new JsonUtil();
                        temp = jsonUtil.parseJsonComment(responseData);
//                        adapter.notifyDataSetChanged();
                        try{
                            sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = '1';
                        GetComment.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
