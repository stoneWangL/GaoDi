package com.example.stonewang.gaodi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.stonewang.gaodi.adapter.LandItemAdapter;
import com.example.stonewang.gaodi.mode.Land;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by stoneWang on 2017/4/8.
 */

public class LandItemActivity extends AppCompatActivity{

    private List<Land> landList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_item);

        initLandItem();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_land_item);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());//添加装饰类,加横线
        LandItemAdapter adapter = new LandItemAdapter(landList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化列表数据
     */
    private void initLandItem() {
        Land tank59 = new Land("59式中型坦克", R.drawable.land_tank59);
        landList.add(tank59);
        Land tank69 = new Land("69式中型坦克", R.drawable.land_tank69);
        landList.add(tank69);
    }
}
