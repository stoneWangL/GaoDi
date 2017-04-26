package com.example.stonewang.gaodi.LocalShowActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.stonewang.gaodi.MyItemDecoration;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.AirforceItemAdapter;
import com.example.stonewang.gaodi.mode.Airforce;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/25.
 */

public class AirforceItemActivity extends AppCompatActivity {
    private List<Airforce> airforceList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_item);

        initAirforceItem();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_land_item);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());//添加装饰类,加横线
        AirforceItemAdapter adapter = new AirforceItemAdapter(airforceList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化列表数据
     */
    private void initAirforceItem() {
        Airforce airforceCJL5 = new Airforce("初教-5教练机", R.drawable.airforce_cjl5);
        airforceList.add(airforceCJL5);
        Airforce airforceCJL6 = new Airforce("初教-6教练机", R.drawable.airforce_cjl6);
        airforceList.add(airforceCJL6);
        Airforce airforceJJL5 = new Airforce("歼教-5教练机", R.drawable.airforce_jjl5);
        airforceList.add(airforceJJL5);
        Airforce airforceJJL7 = new Airforce("歼教-7教练机", R.drawable.airforce_jjl7);
        airforceList.add(airforceJJL7);
        Airforce airforceJL8 = new Airforce("教-8教练机", R.drawable.airforce_jl8);
        airforceList.add(airforceJL8);
        Airforce airforceJL9 = new Airforce("教练-9教练机", R.drawable.airforce_jl9);
        airforceList.add(airforceJL9);
        Airforce airforceJL15 = new Airforce("L-15教练机", R.drawable.airforce_jl15);
        airforceList.add(airforceJL15);
        Airforce airforceJ5 = new Airforce("歼5战斗机", R.drawable.airforce_j5);
        airforceList.add(airforceJ5);
        Airforce airforceJ6 = new Airforce("歼6战斗机", R.drawable.airforce_j6);
        airforceList.add(airforceJ6);
        Airforce airforceJ7 = new Airforce("歼7战斗机", R.drawable.airforce_j7);
        airforceList.add(airforceJ7);
        Airforce airforceJ8 = new Airforce("歼8战斗机", R.drawable.airforce_j8);
        airforceList.add(airforceJ8);
        Airforce airforceJ82 = new Airforce("歼-8Ⅱ战斗机", R.drawable.airforce_j82);
        airforceList.add(airforceJ82);
        Airforce airforceJ9 = new Airforce("歼9战斗机(方案)", R.drawable.airforce_j9);
        airforceList.add(airforceJ9);
        Airforce airforceJ10 = new Airforce("歼10战斗机", R.drawable.airforce_j10);
        airforceList.add(airforceJ10);
        Airforce airforceJ11 = new Airforce("歼11战斗机", R.drawable.airforce_j11);
        airforceList.add(airforceJ11);
        Airforce airforceJ12 = new Airforce("歼12战斗机", R.drawable.airforce_j12);
        airforceList.add(airforceJ12);
        Airforce airforceJ20 = new Airforce("歼20战斗机", R.drawable.airforce_j20);
        airforceList.add(airforceJ20);
        Airforce airforceJ31 = new Airforce("歼31战斗机", R.drawable.airforce_j31);
        airforceList.add(airforceJ31);
        Airforce airforcefc1 = new Airforce("FC-1战斗机(枭龙)", R.drawable.airforce_fc1);
        airforceList.add(airforcefc1);
        Airforce airforcesu27 = new Airforce("苏27战斗机", R.drawable.airforce_su27);
        airforceList.add(airforcesu27);
        Airforce airforcesu30 = new Airforce("苏30战斗机", R.drawable.airforce_su30);
        airforceList.add(airforcesu30);
        Airforce airforcejh7 = new Airforce("歼轰7歼击轰炸机(飞豹)", R.drawable.airforce_jh7);
        airforceList.add(airforcejh7);
        Airforce airforceq5 = new Airforce("强5强击机", R.drawable.airforce_q5);
        airforceList.add(airforceq5);
        Airforce airforceq6 = new Airforce("强-6强击机(方案)", R.drawable.airforce_q6);
        airforceList.add(airforceq6);
        Airforce airforceh5 = new Airforce("轰5轰炸机", R.drawable.airforce_h5);
        airforceList.add(airforceh5);
        Airforce airforceh6 = new Airforce("轰6轰炸机", R.drawable.airforce_h6);
        airforceList.add(airforceh6);
        Airforce airforceyj200 = new Airforce("空警200预警机", R.drawable.airforce_yj200);
        airforceList.add(airforceyj200);
        Airforce airforceyj500 = new Airforce("空警500预警机", R.drawable.airforce_yj500);
        airforceList.add(airforceyj500);
        Airforce airforceyj2000 = new Airforce("空警2000预警机", R.drawable.airforce_yj2000);
        airforceList.add(airforceyj2000);
        Airforce airforcey5 = new Airforce("运5运输机", R.drawable.airforce_y5);
        airforceList.add(airforcey5);
        Airforce airforcey7 = new Airforce("运7运输机", R.drawable.airforce_y7);
        airforceList.add(airforcey7);
        Airforce airforcey8 = new Airforce("运8运输机", R.drawable.airforce_y8);
        airforceList.add(airforcey8);
        Airforce airforcey20 = new Airforce("运20运输机", R.drawable.airforce_y20);
        airforceList.add(airforcey20);
    }
}
