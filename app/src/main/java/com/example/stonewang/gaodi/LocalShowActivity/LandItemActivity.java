package com.example.stonewang.gaodi.LocalShowActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

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
    }
}
