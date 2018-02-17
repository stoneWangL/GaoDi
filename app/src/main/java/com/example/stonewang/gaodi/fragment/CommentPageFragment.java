package com.example.stonewang.gaodi.fragment;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.stonewang.gaodi.LoginActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.CommentAdapter;
import com.example.stonewang.gaodi.db.Comment;
import com.example.stonewang.gaodi.db.JunshiNews;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Thread.sleep;

/**
 * Created by Lenovo on 2018/2/5.
 */

public class CommentPageFragment extends Fragment {
    private List<Comment> commentList = new ArrayList<>(),temp = new ArrayList<>();
    private CommentAdapter adapter;

    private PopupWindow mPopWindow;


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
        Log.d("stone0066","view:"+v.toString());

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);


        //悬浮球，写评论按钮
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.writeComment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        return v;
    }

    /**
     * 提交评论后的UI处理
     */
    private Handler commentNext= new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what){
                case 1://评论提交成功
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("提交中评论");
                    progressDialog.setMessage("提交中...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    commentList.clear();
                    new Thread(){
                        @Override
                        public void run() {
                            try{
                                sleep(800);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }finally {
                                progressDialog.dismiss();
                            }
                        }
                    }.start();
                    temp = DataSupport.where("newsid = " + newsid+ ";" +"news = "+news).find(Comment.class);
                    for (Comment all:temp){
                        commentList.add(all);
                    }
                    adapter.notifyDataSetChanged();

                    break;
                case 0://评论提交失败
                    AlertDialog dialog2 = new AlertDialog.Builder(getContext())
                            .setTitle("评论结果")
                            .setMessage("评论失败！")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                            .create();
                    dialog2.show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 点击悬浮按钮，显示popWindow的评论软键盘
     * 判断是否具有评论权限，并给与提示
     * 处理
     */
    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup, null);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //防止PopupWindow被软件盘挡住（可能只要下面一句，可能需要这两句）
//        mPopWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //弹出软键盘
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //这里给它设置了弹出的时间，
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
        //设置各个控件的点击响应
        final EditText editText = (EditText) contentView.findViewById(R.id.pop_editText);
        Button btn = (Button) contentView.findViewById(R.id.pop_btn);
        //检查是否游客登录的凭证
        final SharedPreferences pref = getActivity().getSharedPreferences("User",MODE_PRIVATE);
        final Boolean notGuest = pref.getBoolean("notGuest",false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notGuest){
                    //用户登录，可以评论
                    final String comment_content = editText.getText().toString();
                    String username = pref.getString("userName","");

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
                                Message message = new Message();
                                if (responseData.equals("1")){
                                    Log.d("stone007","提交成功");

                                    final String comment_content = editText.getText().toString();
                                    final String username = pref.getString("userName","");
                                    //获取本地数据库最后一个评论id
                                    Comment now = DataSupport.findLast(Comment.class);
                                    int ComId = now.getCommentId()+1;

                                    Comment oneComment = new Comment();
                                    oneComment.setCommentId(ComId);
                                    oneComment.setNews(news);
                                    oneComment.setNewsid(newsid);
                                    oneComment.setAuthor(username);
                                    oneComment.setContent(comment_content);
                                    oneComment.setTime(getNowTime());
                                    oneComment.save();


//                                    try{
//                                        sleep(2000);
//                                    }catch (InterruptedException e){
//                                        e.printStackTrace();
//                                    }
                                    message.what = 1;

                                }else{
                                    //提交失败
                                    message.what = 0;
                                }
                                commentNext.sendMessage(message);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    //参数：1，自己的EditText。2，时间。
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    mPopWindow.dismiss();
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

        //是否具有获取焦点的能力
        mPopWindow.setFocusable(true);
        //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    //获取Android系统当前时间
    public static String getNowTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
