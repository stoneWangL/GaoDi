package com.example.stonewang.gaodi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.adapter.LocalItemAdapter;
import com.example.stonewang.gaodi.mode.Local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/7.
 */

public class LocalFragment extends Fragment {

    private List<Local> localList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.local_fragment_layout, container, false);
        initLocal();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.local_recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager (1,StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        LocalItemAdapter adapter = new LocalItemAdapter(localList);
        recyclerView.setAdapter(adapter);

        return v;
    }
//this.getString(R.string.XXX);
    private void initLocal() {
        Local Land = new Local("中国陆军", R.drawable.landarmy_item,this.getString(R.string.itemLandIntroduce));
        localList.add(Land);
        Local Navy = new Local("中国海军", R.drawable.navy_item, this.getString(R.string.itemNavyIntroduce));
        localList.add(Navy);
        Local Air = new Local("中国空军", R.drawable.airarmy_item, this.getString(R.string.itemAirIntroduce));
        localList.add(Air);
    }
}
