package com.example.stonewang.gaodi.LocalShowActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.stonewang.gaodi.MyItemDecoration;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.LandItemAdapter;
import com.example.stonewang.gaodi.mode.Land;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/8.
 */

public class LandItemActivity extends AppCompatActivity{

    private List<Land> landList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_item);

        initLandItem();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_land_item);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());//添加装饰类,加横线
        LandItemAdapter adapter = new LandItemAdapter(landList);
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
    private void initLandItem() {
        Land tank59 = new Land("59式中型坦克", R.drawable.land_tank59);
        landList.add(tank59);
        Land tank69 = new Land("69式中型坦克", R.drawable.land_tank69);
        landList.add(tank69);
        Land tank79 = new Land("79式中型坦克", R.drawable.land_tank79);
        landList.add(tank79);
        Land tank80 = new Land("80式中型坦克", R.drawable.land_tank80);
        landList.add(tank80);
        Land tank85 = new Land("85式主战坦克", R.drawable.land_tank85);
        landList.add(tank85);
        Land tank90 = new Land("90式主战坦克", R.drawable.land_tank90);
        landList.add(tank90);
        Land tank96 = new Land("96式主战坦克", R.drawable.land_tank96);
        landList.add(tank96);
        Land tank98 = new Land("98式主战坦克", R.drawable.land_tank98);
        landList.add(tank98);
        Land tank99 = new Land("99式主战坦克", R.drawable.land_tank99);
        landList.add(tank99);
        Land tank62 = new Land("62式轻型坦克", R.drawable.land_tank62);
        landList.add(tank62);
        Land tank63 = new Land("63式水陆坦克", R.drawable.land_tank63);
        landList.add(tank63);
        Land tank09 = new Land("09式轮式装甲车", R.drawable.land_tank09);
        landList.add(tank09);
        Land tank10 = new Land("PLZ-05自行榴弹炮", R.drawable.land_plz05);
        landList.add(tank10);
        Land helicopterz5 = new Land("直5直升机", R.drawable.airforce_z5);
        landList.add(helicopterz5);
        Land helicopterz8 = new Land("直8直升机", R.drawable.airforce_z8);
        landList.add(helicopterz8);
        Land helicopterz9 = new Land("直9直升机", R.drawable.airforce_z9);
        landList.add(helicopterz9);
        Land helicopterwz10 = new Land("武直10武装直升机", R.drawable.airforce_wz10);
        landList.add(helicopterwz10);
        Land helicopterwz19 = new Land("武直19武装直升机", R.drawable.airforce_wz19);
        landList.add(helicopterwz19);
        Land helicopterm171 = new Land("米-171直升机", R.drawable.airforce_m171);
        landList.add(helicopterm171);
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
