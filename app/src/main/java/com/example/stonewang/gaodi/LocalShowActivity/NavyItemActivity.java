package com.example.stonewang.gaodi.LocalShowActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.stonewang.gaodi.MyItemDecoration;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.NavyItemAdapter;
import com.example.stonewang.gaodi.mode.Navy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/22.
 */

public class NavyItemActivity extends AppCompatActivity {
    private List<Navy> navyList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_item);

        initNavyItem();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_land_item);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());//添加装饰类,加横线
        NavyItemAdapter adapter = new NavyItemAdapter(navyList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_local_item);
        toolbar.setTitle(R.string.LocalData);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    /**
     * 初始化列表数据
     */
    private void initNavyItem() {
        Navy varship001 = new Navy("001A型航空母舰", R.drawable.navy_ship001);
        navyList.add(varship001);
        Navy varship051 = new Navy("051型驱逐舰", R.drawable.navy_ship051);
        navyList.add(varship051);
        Navy varship051b = new Navy("051B型驱逐舰", R.drawable.navy_ship051b);
        navyList.add(varship051b);
        Navy varship051c = new Navy("051C型驱逐舰", R.drawable.navy_ship051c);
        navyList.add(varship051c);
        Navy varship052 = new Navy("052型驱逐舰", R.drawable.navy_ship052);
        navyList.add(varship052);
        Navy varship052b = new Navy("052B型驱逐舰", R.drawable.navy_ship052b);
        navyList.add(varship052b);
        Navy varship052c = new Navy("052C型驱逐舰", R.drawable.navy_ship052c);
        navyList.add(varship052c);
        Navy varship052d = new Navy("052D型驱逐舰", R.drawable.navy_ship052d);
        navyList.add(varship052d);
        Navy varship053 = new Navy("053型护卫舰", R.drawable.navy_ship053);
        navyList.add(varship053);
        Navy varship054 = new Navy("054型护卫舰", R.drawable.navy_ship054);
        navyList.add(varship054);
        Navy varship054A = new Navy("054A型护卫舰", R.drawable.navy_ship0544a);
        navyList.add(varship054A);
        Navy varship056 = new Navy("056型护卫舰", R.drawable.navy_ship056);
        navyList.add(varship056);
        Navy varship071 = new Navy("071型船坞登陆舰", R.drawable.navy_ship071);
        navyList.add(varship071);
        Navy varship091 = new Navy("091型核潜艇", R.drawable.navy_ship091);
        navyList.add(varship091);
        Navy varship092 = new Navy("092型核潜艇", R.drawable.navy_ship092);
        navyList.add(varship092);
        Navy varship093 = new Navy("093型核潜艇", R.drawable.navy_ship093);
        navyList.add(varship093);
        Navy varship094 = new Navy("094型核潜艇", R.drawable.navy_ship094);
        navyList.add(varship094);
        Navy varship035 = new Navy("035型常规动力潜艇", R.drawable.navy_ship035);
        navyList.add(varship035);
        Navy varship039 = new Navy("039型常规动力潜艇", R.drawable.navy_ship039);
        navyList.add(varship039);
        Navy varshipjiluo = new Navy("基洛级常规动力潜艇", R.drawable.navy_shipjiluo);
        navyList.add(varshipjiluo);
        Navy varshipyeniu = new Navy("中国野牛气垫船", R.drawable.navy_shipyeniu);
        navyList.add(varshipyeniu);
        Navy airforcesh5 = new Navy("水轰5反潜轰炸机", R.drawable.airforce_sh5);
        navyList.add(airforcesh5);
        Navy airforceJ15 = new Navy("歼15舰载战斗机", R.drawable.airforce_j15);
        navyList.add(airforceJ15);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("User",MODE_PRIVATE);
        String color = pref.getString("color","one");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_local_item);


        if (color.equals("two")){
            toolbar.setBackgroundResource(R.color.colorBarBg2);
        }else{
            toolbar.setBackgroundResource(R.color.colorBarBg);
        }
    }
}
