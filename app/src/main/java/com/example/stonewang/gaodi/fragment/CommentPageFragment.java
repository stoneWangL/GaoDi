package com.example.stonewang.gaodi.fragment;

import android.content.Context;
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
import com.example.stonewang.gaodi.MyItemDecoration;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.CommentAdapter;
import com.example.stonewang.gaodi.db.Comment;
import com.example.stonewang.gaodi.db.GuojiNews;
import com.example.stonewang.gaodi.db.JunshiNews;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);

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
