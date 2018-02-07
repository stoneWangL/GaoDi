package com.example.stonewang.gaodi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stonewang.gaodi.JunshiNewsActivity;
import com.example.stonewang.gaodi.R;

/**
 * Created by Lenovo on 2018/2/5.
 */

public class CommentPageFragment extends Fragment {

    private String[] data = {
            "陈赓出身军人世家，他的老家和毛主席家就隔一座山，上小学是在东山小学，也就是毛主席读过的小学。",
            "还有一个原因，就是陈赓内心对李云龙其实是比较欣赏的，只是认为李云龙性格桀骜不驯，需要恩威并济，所以有时候批评他很严厉，但其实也是比较照顾他，爱护他的。李云龙对此心知肚明，真人面前不说假话，当然知道要对陈赓说实话。",
            "陈赓黄埔一期毕业，和党内徐向前、左权、周士弟等人同学。陈赓在鄂豫皖时就是师长，那时还没有四方面军一说，就一个军的架子，比李云龙大，陈赓受伤后离开鄂豫皖…后到苏区担任红军大学校长、长征时任干部团团长。陈赓参加南昌起义任营长，后来又赴苏联学习特工，任过中央特科科长，顾顺章叛变后，又是他送情报、协助周恩来掩护中央完美撤退，他成为一个传奇。",
            "李云龙这个人物真实不真实还值得商榷，但是陈赓大将可是响当当的人物呀。出身将门，其祖父就是湘军将领。"
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tab2, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return v;
    }
}
