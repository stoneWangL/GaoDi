package com.example.stonewang.gaodi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.stonewang.gaodi.adapter.NewsFragmentAdapter;
import com.example.stonewang.gaodi.fragment.CommentPageFragment;
import com.example.stonewang.gaodi.fragment.NewsPageFragment;
import com.example.stonewang.gaodi.util.MyPageChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/3/17.
 */

public class JunshiNewsActivity extends AppCompatActivity {
    private ViewPager myViewPager;


    private ArrayList<Fragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent intent = getIntent();
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
    public void Comment_right(View view){

        myViewPager.arrowScroll(2);

    }
    //向前翻页
    public void News_left(View view){
        myViewPager.arrowScroll(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("User",MODE_PRIVATE);
        String color = pref.getString("color","one");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_news);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_news);

        if (color.equals("two")){
            toolbar.setBackgroundResource(R.color.colorBarBg2);
            relativeLayout.setBackgroundResource(R.color.colorBarBg3);
        }else{
            toolbar.setBackgroundResource(R.color.colorBarBg);
            relativeLayout.setBackgroundResource(R.color.colorInActive);
        }
    }
}
