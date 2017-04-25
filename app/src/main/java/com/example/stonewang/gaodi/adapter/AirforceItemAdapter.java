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
import com.example.stonewang.gaodi.LocalShowActivity.AirforceShowOneActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.AirforceDescribe;
import com.example.stonewang.gaodi.mode.Airforce;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/25.
 */

public class AirforceItemAdapter extends RecyclerView.Adapter<AirforceItemAdapter.ViewHolder>{
    private Context mContext;
    private List<Airforce> mAirforceList;
    private List<AirforceDescribe> mAirforceDescribes = new ArrayList<>();//全局变量mAirforceDescribes

    static class ViewHolder extends RecyclerView.ViewHolder{
        View airforceView;
        ImageView airforceImage;
        TextView airforceName;

        public ViewHolder(View view){
            super(view);
            airforceView = view;
            airforceImage = (ImageView) view.findViewById(R.id.land_item_image);
            airforceName = (TextView) view.findViewById(R.id.land_item_name);
        }
    }

    public AirforceItemAdapter(List<Airforce> airforceList){
        mAirforceList = airforceList;
    }

    @Override
    public AirforceItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.land_item, parent, false);
        final AirforceItemAdapter.ViewHolder holder = new AirforceItemAdapter.ViewHolder(view);

        //点击陆军某武器
        holder.airforceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAirforceDescribes = DataSupport.findAll(AirforceDescribe.class);
                int position = holder.getAdapterPosition();
                AirforceDescribe airforceDescribe = mAirforceDescribes.get(position);
                Intent intent = new Intent(mContext, AirforceShowOneActivity.class);

                intent.putExtra("name", airforceDescribe.getName());
                intent.putExtra("imageId", airforceDescribe.getImageId());
                intent.putExtra("baseParameter", airforceDescribe.getBaseParameter());
                intent.putExtra("historicalBackground", airforceDescribe.getHistoricalBackground());
                intent.putExtra("detailedInstroduction", airforceDescribe.getDetailedInstroduction());
                intent.putExtra("sumUp", airforceDescribe.getSumUp());

                mContext.startActivity(intent);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(AirforceItemAdapter.ViewHolder holder, int position) {
        Airforce airforce = mAirforceList.get(position);
        Glide.with(mContext).load(airforce.getImageId()).into(holder.airforceImage);
        holder.airforceName.setText(airforce.getName());
    }

    @Override
    public int getItemCount() {
        return mAirforceList.size();
    }
}
