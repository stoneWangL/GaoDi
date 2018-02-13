package com.example.stonewang.gaodi.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stonewang.gaodi.LoginActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.CommentAdapter;
import com.example.stonewang.gaodi.db.Comment;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Lenovo on 2018/2/5.
 */

public class CommentPageFragment extends Fragment {
    private List<Comment> commentList = new ArrayList<>(),temp = new ArrayList<>();
    private CommentAdapter adapter;

    private int newsid;
    private String news;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("stone0066","onAttach");

        commentList.clear();

        Intent intent = getActivity().getIntent();
        newsid = intent.getIntExtra("NewsId",0);
        news = intent.getStringExtra("News");

        commentList = DataSupport.where("newsid = " + newsid+ ";" +"news = "+news).find(Comment.class);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tab2, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_comment);
        final EditText commentEditText = (EditText) v.findViewById(R.id.comment_editText);
        Button commentButton = (Button) v.findViewById(R.id.upComment_button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("User",MODE_PRIVATE);
                Boolean notGuest = pref.getBoolean("notGuest",false);
                if (notGuest){
                    //用户登录，可以评论
                    final String comment_content = commentEditText.getText().toString();
                    final String username = pref.getString("userName","");

                    final String url = "http://114.67.243.127/index.php/API/Api/addComment/news/"+news+"/newsid/"+
                            newsid+"/author/"+username+"/content/"+comment_content;

                    //然后开一个线程，提交评论给服务器，如果提交成功，则在本地commentList增加一条，并且更新adapter，否则提示提交失败
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(url)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String responseData = response.body().string();
                                //将API返回的json数据传递给处理函数
                                Log.d("stone11","登录返回数据:"+responseData);
                                if (responseData.equals("1")){
//                                    Log.d("stone007","提交成功");
//                                    AlertDialog dialog = new AlertDialog.Builder(getContext())
//                                            .setTitle("评论结果")
//                                            .setMessage("恭喜您，评论成功！")
//                                            .setPositiveButton("OK",
//                                                    new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            commentEditText.setText("");
//                                                            Comment oneComment = new Comment();
//                                                            oneComment.setNews(news);
//                                                            oneComment.setNewsid(newsid);
//                                                            oneComment.setAuthor(username);
//                                                            oneComment.setContent(comment_content);
//                                                            commentList.add(oneComment);
//
//                                                            adapter.notifyDataSetChanged();
//
//                                                            dialog.dismiss();
//                                                        }
//                                                    })
//                                            .create();
//                                    dialog.show();
                                }else{
                                    //提交失败
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else {
                    //游客登录，没有权限评论
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle("评论需要登录")
                            .setMessage("请登录")
                            .setPositiveButton("登录",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = getActivity().getIntent();
                                            intent.setClass(getActivity(),LoginActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                    })
                            .setNegativeButton("不登录",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                            .create();
                    dialog.show();
                }
            }
        });


        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
//        adapter.notifyDataSetChanged();
        Log.d("stone0066","onResume");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        temp.clear();commentList.clear();
        Log.d("stone0066","onDetach");
    }
}
