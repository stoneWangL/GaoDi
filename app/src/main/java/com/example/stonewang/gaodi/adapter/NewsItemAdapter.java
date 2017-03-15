package com.example.stonewang.gaodi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.GaoDiNews;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by stoneWang on 2017/3/12.
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder>{
    private List<GaoDiNews> mGaoDiNewsesList;

    /**
     * View参数：通常就是RecyclerView子项的最外层布局，此处即choose_area.xml
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView NewsImage;
        TextView NewsTitle;
        public ViewHolder(View view){
            super(view);
            NewsImage = (ImageView) view.findViewById(R.id.news_title_item_image);
            NewsTitle = (TextView) view.findViewById(R.id.news_title_item_name);
        }
    }

    public NewsItemAdapter(List<GaoDiNews> gaoDiNewsesList){
        mGaoDiNewsesList = gaoDiNewsesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_title_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GaoDiNews gaoDiNews = mGaoDiNewsesList.get(position);
//        holder.NewsImage.setImageResource(gaoDiNews.getThumbnail_pic_s());
        holder.NewsTitle.setText(gaoDiNews.getTitle());
    }

    @Override
    public int getItemCount() {
        return mGaoDiNewsesList.size();
    }
}


//public class NewsItemAdapter extends ArrayAdapter<GaoDiNews> {
//    private int resourceId;
//
//    /**
//     *
//     * @param context 上下文
//     * @param textViewResourceId ListView子项布局id
//     * @param objects 数据
//     */
//    public NewsItemAdapter(Context context, int textViewResourceId, List<GaoDiNews> objects){
//        super(context, textViewResourceId, objects);
//        resourceId = textViewResourceId;
//    }
//
//    /**
//     *getView 方法在每个子项被滚动到屏幕内的时候会被调用
//     * @param position
//     * @param convertView 用于将之前加载好的布局进行缓存
//     * @param parent
//     * @return
//     */
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        GaoDiNews gaoDiNews = getItem(position);// 获取当前的GaoDiNews实例
//        View view;
//        ViewHolder viewHolder;
//        //判断是否有缓存的布局
//        if(convertView == null){
//            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.NewsImage = (ImageView) view.findViewById(R.id.news_title_item_image);
//            viewHolder.NewsTitle = (TextView) view.findViewById(R.id.news_title);
//            view.setTag(viewHolder); //将viewHolder储存在View中
//        }else{
//            view = convertView;
//            viewHolder = (ViewHolder) view.getTag(); //重新获取 ViewHolder
//        }
////        viewHolder.NewsImage.setImageResource(gaoDiNews.getThumbnail_pic_s());
//        viewHolder.NewsTitle.setText(gaoDiNews.getTitle());
//        return view;
//    }
//
//    class ViewHolder{
//        ImageView NewsImage;
//
//        TextView NewsTitle;
//    }
//
//
//}
