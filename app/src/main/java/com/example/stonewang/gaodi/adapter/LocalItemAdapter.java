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
import com.example.stonewang.gaodi.mode.Local;

import java.util.List;

/**
 * Created by stoneWang on 2017/4/8.
 */

public class LocalItemAdapter extends RecyclerView.Adapter<LocalItemAdapter.ViewHolder>{
    private Context mContext;
    private List<Local> mLocalList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView localImage;
        TextView localName;
        TextView introduce;

        public ViewHolder(View view){
            super(view);
            localImage = (ImageView) view.findViewById(R.id.local_item_image);
            localName = (TextView) view.findViewById(R.id.local_item_name);
            introduce = (TextView) view.findViewById(R.id.local_item_introduce);
        }
    }

    public LocalItemAdapter(List<Local> localList){
        mLocalList = localList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.local_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Local local = mLocalList.get(position);
//        holder.localImage.setImageResource(local.getImageId());
        Glide.with(mContext).load(local.getImageId()).into(holder.localImage);
        holder.localName.setText(local.getName());
        holder.introduce.setText(local.getIntroduce());
    }

    @Override
    public int getItemCount() {
        return mLocalList.size();
    }
}
