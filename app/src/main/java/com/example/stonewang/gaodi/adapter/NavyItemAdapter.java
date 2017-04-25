package com.example.stonewang.gaodi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.stonewang.gaodi.LocalShowActivity.NavyShowOneActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.NavyDescribe;
import com.example.stonewang.gaodi.mode.Navy;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/8.
 */

public class NavyItemAdapter extends RecyclerView.Adapter<NavyItemAdapter.ViewHolder>{
    private Context mContext;
    private List<Navy> mNavyList;
    private List<NavyDescribe> mNavyDescribes = new ArrayList<>();//全局变量mNavyDescribes

    static class ViewHolder extends RecyclerView.ViewHolder{
        View navyView;
        ImageView navyImage;
        TextView navyName;

        public ViewHolder(View view){
            super(view);
            navyView = view;
            navyImage = (ImageView) view.findViewById(R.id.land_item_image);
            navyName = (TextView) view.findViewById(R.id.land_item_name);
        }
    }

    public NavyItemAdapter(List<Navy> navyList){
        mNavyList = navyList;
    }

    @Override
    public NavyItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.land_item, parent, false);
        final ViewHolder holder = new NavyItemAdapter.ViewHolder(view);

        //点击陆军某武器
        holder.navyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavyDescribes = DataSupport.findAll(NavyDescribe.class);
                int position = holder.getAdapterPosition();
                NavyDescribe navyDescribe = mNavyDescribes.get(position);
                Intent intent = new Intent(mContext, NavyShowOneActivity.class);

                intent.putExtra("name", navyDescribe.getName());
                intent.putExtra("imageId", navyDescribe.getImageId());
                intent.putExtra("baseParameter", navyDescribe.getBaseParameter());
                intent.putExtra("historicalBackground", navyDescribe.getHistoricalBackground());
                intent.putExtra("detailedInstroduction", navyDescribe.getDetailedInstroduction());
                intent.putExtra("sumUp", navyDescribe.getSumUp());

                mContext.startActivity(intent);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(NavyItemAdapter.ViewHolder holder, int position) {
        Navy navy = mNavyList.get(position);
        Glide.with(mContext).load(navy.getImageId()).into(holder.navyImage);
        holder.navyName.setText(navy.getName());
    }

    @Override
    public int getItemCount() {
        return mNavyList.size();
    }
}
