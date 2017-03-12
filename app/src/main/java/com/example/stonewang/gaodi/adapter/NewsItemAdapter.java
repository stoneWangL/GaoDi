package com.example.stonewang.gaodi.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.GaoDiNews;

import java.net.URL;
import java.util.List;

/**
 * Created by stoneWang on 2017/3/12.
 */

public class NewsItemAdapter extends ArrayAdapter<GaoDiNews> {
    private int resourceId;

    public NewsItemAdapter(Context context, int textViewResourceId, List<GaoDiNews> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GaoDiNews gaoDiNews = getItem(position);// 获取当前的GaoDiNews实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView NewsImage = (ImageView) view.findViewById(R.id.news_title_item_image);
        TextView NewsTitle = (TextView) view.findViewById(R.id.news_title);
        NewsTitle.setText(gaoDiNews.getTitle());
        try{
//            String pic_url = gaoDiNews.getThumbnail_pic_s();
//            URL url = new URL(pic_url);
//            NewsImage.setImageResource(BitmapFactory.decodeStream(url.openStream()));
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;

    }
}
