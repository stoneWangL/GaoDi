package com.example.stonewang.gaodi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.mode.Land;

import java.util.List;

/**
 * Created by stoneWang on 2017/4/8.
 */

public class LandItemAdapter extends RecyclerView.Adapter<LandItemAdapter.ViewHolder>{
    private Context mContext;
    private List<Land> mLandList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView landImage;
        TextView landName;

        public ViewHolder(View view){
            super(view);
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
        LandItemAdapter.ViewHolder holder = new LandItemAdapter.ViewHolder(view);
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
