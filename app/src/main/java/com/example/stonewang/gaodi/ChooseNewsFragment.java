package com.example.stonewang.gaodi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.stonewang.gaodi.db.GaoDiNews;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/3/5.
 */

public class ChooseNewsFragment extends Fragment {
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    /**
     * 选中的News
     */
    private GaoDiNews selectNews;
    /**
     * News列表
     */
    private List<GaoDiNews> gaoDiNewsList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);

        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectNews = gaoDiNewsList.get(position);
                queryNews();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    /**
     * 查询选中的News，去数据库上查询
     */
    private void queryNews(){
        gaoDiNewsList = DataSupport.where("uniquekey = ?", String.valueOf(selectNews.getUniquekey())).find(GaoDiNews.class);
        if (gaoDiNewsList.size() > 0){
            dataList.clear();
            for (GaoDiNews gaoDiNews : gaoDiNewsList){
                dataList.add(gaoDiNews.getTitle());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
        }
    }
}
