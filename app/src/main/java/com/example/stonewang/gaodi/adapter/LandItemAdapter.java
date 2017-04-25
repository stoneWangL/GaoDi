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
import com.example.stonewang.gaodi.LocalShowActivity.LandShowOneActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.LandArmyDescribe;
import com.example.stonewang.gaodi.mode.Land;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/8.
 */

public class LandItemAdapter extends RecyclerView.Adapter<LandItemAdapter.ViewHolder>{
    private Context mContext;
    private List<Land> mLandList;
    private List<LandArmyDescribe> mLandArmyDescribes = new ArrayList<>();//全局变量mLandArmyDescribes

    static class ViewHolder extends RecyclerView.ViewHolder{
        View landView;
        ImageView landImage;
        TextView landName;

        public ViewHolder(View view){
            super(view);
            landView = view;
            landImage = (ImageView) view.findViewById(R.id.land_item_image);
            landName = (TextView) view.findViewById(R.id.land_item_name);
        }
    }

    public LandItemAdapter(List<Land> landList){
        mLandList = landList;
    }

    @Override
    public LandItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.land_item, parent, false);
        final ViewHolder holder = new LandItemAdapter.ViewHolder(view);

        //点击陆军某武器
        holder.landView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLandArmyDescribes = DataSupport.findAll(LandArmyDescribe.class);
                int position = holder.getAdapterPosition();
                LandArmyDescribe landArmyDescribe = mLandArmyDescribes.get(position);
                Intent intent = new Intent(mContext, LandShowOneActivity.class);

                intent.putExtra("name", landArmyDescribe.getName());
                intent.putExtra("imageId", landArmyDescribe.getImageId());
                intent.putExtra("baseParameter", landArmyDescribe.getBaseParameter());
                intent.putExtra("historicalBackground", landArmyDescribe.getHistoricalBackground());
                intent.putExtra("detailedInstroduction", landArmyDescribe.getDetailedInstroduction());
                intent.putExtra("sumUp", landArmyDescribe.getSumUp());

                mContext.startActivity(intent);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(LandItemAdapter.ViewHolder holder, int position) {
        Land land = mLandList.get(position);
        Glide.with(mContext).load(land.getImageId()).into(holder.landImage);
        holder.landName.setText(land.getName());
    }

    @Override
    public int getItemCount() {
        return mLandList.size();
    }
}
