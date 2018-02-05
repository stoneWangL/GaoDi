package com.example.stonewang.gaodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.stonewang.gaodi.adapter.NewsFragmentAdapter;
import com.example.stonewang.gaodi.fragment.CommentPageFragment;
import com.example.stonewang.gaodi.fragment.NewsPageFragment;
import com.example.stonewang.gaodi.util.MyPageChangeListener;

import java.util.ArrayList;

/**
 * Created by stoneWang on 2017/4/28.
 */

public class GuojiNewsActivity extends AppCompatActivity {
    private ViewPager myViewPager;


    private ArrayList<Fragment> mFragmentList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent intent = getIntent();
        String url = intent.getStringExtra("NewsUrl");
        String title = intent.getStringExtra("NewsTitle");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_news);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        myViewPager = (ViewPager) findViewById(R.id.myViewPager);

        mFragmentList = new ArrayList<>();

        NewsPageFragment fragment1 = new NewsPageFragment();
        CommentPageFragment fragment2 = new CommentPageFragment();

        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        //实例化的ViewPager绑定适配器
        myViewPager.setAdapter(new NewsFragmentAdapter(getSupportFragmentManager(), mFragmentList));
        //实例化的ViewPager添加滑动监听
        myViewPager.addOnPageChangeListener(new MyPageChangeListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //向后翻页
    public void Comment_click(View view){
        myViewPager.arrowScroll(2);
    }
    //向前翻页
    public void News_click(View view){
        myViewPager.arrowScroll(1);
    }
}
