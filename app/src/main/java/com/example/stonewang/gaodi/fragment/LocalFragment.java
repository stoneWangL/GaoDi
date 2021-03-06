package com.example.stonewang.gaodi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stonewang.gaodi.MyItemDecoration;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.LocalItemAdapter;
import com.example.stonewang.gaodi.mode.Local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/7.
 */

public class LocalFragment extends Fragment {
    private String tab = "\b\b\b\b\b\b";

    private List<Local> localList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.local_fragment_layout, container, false);
        initLocal();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.local_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager (getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());//添加装饰类,加横线
        LocalItemAdapter adapter = new LocalItemAdapter(localList);
        recyclerView.setAdapter(adapter);

        return v;
    }

    private void initLocal() {
        Local Land = new Local("中国陆军", R.drawable.land_tank99,tab+this.getString(R.string.itemLandIntroduce));
        localList.add(Land);
        Local Navy = new Local("中国海军", R.drawable.navy_ship001, tab+this.getString(R.string.itemNavyIntroduce));
        localList.add(Navy);
        Local Air = new Local("中国空军", R.drawable.airforce_j10, tab+this.getString(R.string.itemAirIntroduce));
        localList.add(Air);
    }
}
