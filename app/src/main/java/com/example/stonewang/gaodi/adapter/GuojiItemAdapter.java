package com.example.stonewang.gaodi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.stonewang.gaodi.GuojiNewsActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.GuojiNews;
import java.util.List;

/**
 * Created by stoneWang on 2017/4/28.
 * 国际News的itemAdapter
 */

public class GuojiItemAdapter extends RecyclerView.Adapter<GuojiItemAdapter.ViewHolder>{
    private Context mContext;
    private List<GuojiNews> mGuojiNewsList;//全局变量mGuojiNewsList

    /**
     * View参数：通常就是RecyclerView子项的最外层布局，此处即choose_area.xml
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        View GuojiNewsView;
        ImageView NewsImage;
        TextView NewsTitle;
        TextView NewsDate;
        TextView NewsAuthor;
        public ViewHolder(View view){
            super(view);
            GuojiNewsView = view;
            NewsImage = (ImageView) view.findViewById(R.id.news_title_item_image);
            NewsTitle = (TextView) view.findViewById(R.id.news_title_item_name);
            NewsDate = (TextView) view.findViewById(R.id.news_item_time);
            NewsAuthor = (TextView) view.findViewById(R.id.news_item_author);
        }
    }

    /**
     * 构造函数
     * @param guojiNewsList 要展示的数据源(传入)
     */
    public GuojiItemAdapter(List<GuojiNews> guojiNewsList){
        mGuojiNewsList = guojiNewsList;
    }

    /**
     * 由于NewsItemAdapter继承自RecyclerView.Adapter,所以必须重写onCreateViewHolder
     * 用于创建ViewHolder实例，在这个实例中加载自定义item布局（news_title_item）
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public GuojiItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_title_item, parent, false);//创建一个ViewHolder实例
        final GuojiItemAdapter.ViewHolder holder = new GuojiItemAdapter.ViewHolder(view); //将加载的布局传入到构造函数ViewHolder中
        holder.GuojiNewsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                GuojiNews guojiNews = mGuojiNewsList.get(position);
                Toast.makeText(v.getContext(), "图片不错吧", Toast.LENGTH_SHORT).show();
            }
        });
        holder.NewsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position  = holder.getAdapterPosition();
                GuojiNews guojiNews = mGuojiNewsList.get(position);
                Intent intent = new Intent(v.getContext(), GuojiNewsActivity.class);
                intent.putExtra("NewsUrl", guojiNews.getUrl());
                v.getContext().startActivity(intent);
            }
        });
        return holder;//返回ViewHolder实例
    }

    /**
     * 由于NewsItemAdapter继承自RecyclerView.Adapter,所以必须重写onBindViewHolder
     * 用于对RecyclerView子项数据进行赋值，会在每个子项滚到屏幕内的时候执行，通过position参数得到当前项的GaoDiNews实例，
     * 然后将数据设置到ViewHolder的ImageView和TextView即可
     * @param holder ViewHolder实例
     * @param position 当前屏幕内的位置
     */
    @Override
    public void onBindViewHolder(GuojiItemAdapter.ViewHolder holder, int position) {
        GuojiNews guojiNews = mGuojiNewsList.get(position);
        holder.NewsTitle.setText(guojiNews.getTitle());
        Glide.with(mContext).load(guojiNews.getThumbnail_pic_s()).into(holder.NewsImage);
        holder.NewsDate.setText("日期: "+guojiNews.getDate()+"    ");
        holder.NewsAuthor.setText("来源: "+guojiNews.getAuthor_name());
    }

    /**
     * 由于NewsItemAdapter继承自RecyclerView.Adapter,所以必须重写getItemCount
     * 告诉RecycleView一共有多少项
     * @return 返回数据源的长度
     */
    @Override
    public int getItemCount() {
        return mGuojiNewsList.size();
    }
}
