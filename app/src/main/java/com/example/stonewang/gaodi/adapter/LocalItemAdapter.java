package com.example.stonewang.gaodi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stonewang.gaodi.LandItemActivity;
import com.example.stonewang.gaodi.NavyItemActivity;
import com.example.stonewang.gaodi.NewsActivity;
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
        View localView;
        ImageView localImage;
        TextView localName;
        TextView introduce;

        public ViewHolder(View view){
            super(view);
            localView = view;
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

        final ViewHolder holder = new ViewHolder(view);

        holder.localView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position == 0)//点击陆军
                {
                    Intent intent = new Intent(v.getContext(), LandItemActivity.class);
                    v.getContext().startActivity(intent);
                }else if (position == 1)//点击空军
                {
                    Intent intent = new Intent(v.getContext(), NavyItemActivity.class);
                    v.getContext().startActivity(intent);
//                    Toast.makeText(mContext, "点击了空军", Toast.LENGTH_SHORT).show();
                }else if (position == 2)//点击海军
                {
                    Toast.makeText(mContext, "点击了海军", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
