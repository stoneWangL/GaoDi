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
import com.example.stonewang.gaodi.NewsActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.JunshiNews;

import java.util.List;

/**
 * Created by stoneWang on 2017/3/12.
 * fragment页面的数据由Adapter来设置
 * RecyclerView 的所有点击事件交给View去注册
 *
 */
public class JunshiItemAdapter extends RecyclerView.Adapter<JunshiItemAdapter.ViewHolder>{
    private Context mContext;
    private List<JunshiNews> mJunshiNewsList;//全局变量mJunshiNewsesList

    /**
     * View参数：通常就是RecyclerView子项的最外层布局，此处即choose_area.xml
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        View JunshiNewsView;
        ImageView NewsImage;
        TextView NewsTitle;
        TextView NewsDate;
        TextView NewsAuthor;
        public ViewHolder(View view){
            super(view);
            JunshiNewsView = view;
            NewsImage = (ImageView) view.findViewById(R.id.news_title_item_image);
            NewsTitle = (TextView) view.findViewById(R.id.news_title_item_name);
            NewsDate = (TextView) view.findViewById(R.id.news_item_time);
            NewsAuthor = (TextView) view.findViewById(R.id.news_item_author);
        }
    }

    /**
     * 构造函数
     * @param JunshiNewsList 要展示的数据源(传入)
     */
    public JunshiItemAdapter(List<JunshiNews> JunshiNewsList){
        mJunshiNewsList = JunshiNewsList;
    }

    /**
     * 由于NewsItemAdapter继承自RecyclerView.Adapter,所以必须重写onCreateViewHolder
     * 用于创建ViewHolder实例，在这个实例中加载自定义item布局（news_title_item）
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         if (mContext == null){
             mContext = parent.getContext();
         }
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_title_item, parent, false);//创建一个ViewHolder实例
        final ViewHolder holder = new ViewHolder(view); //将加载的布局传入到构造函数ViewHolder中
        holder.JunshiNewsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                JunshiNews junshiNews = mJunshiNewsList.get(position);
                Toast.makeText(v.getContext(), "图片不错吧", Toast.LENGTH_SHORT).show();
            }
        });
        holder.NewsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position  = holder.getAdapterPosition();
                JunshiNews junshiNews = mJunshiNewsList.get(position);
                Intent intent = new Intent(v.getContext(), NewsActivity.class);
                intent.putExtra("NewsUrl", junshiNews.getUrl());
                v.getContext().startActivity(intent);
            }
        });
        return holder;//返回ViewHolder实例
    }

    /**
     * 由于NewsItemAdapter继承自RecyclerView.Adapter,所以必须重写onBindViewHolder
     * 用于对RecyclerView子项数据进行赋值，会在每个子项滚到屏幕内的时候执行，通过position参数得到当前项的JunshiNews实例，
     * 然后将数据设置到ViewHolder的ImageView和TextView即可
     * @param holder ViewHolder实例
     * @param position 当前屏幕内的位置
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JunshiNews junshiNews = mJunshiNewsList.get(position);
        holder.NewsTitle.setText(junshiNews.getTitle());
        Glide.with(mContext).load(junshiNews.getThumbnail_pic_s()).into(holder.NewsImage);
        holder.NewsDate.setText("日期: "+junshiNews.getDate()+"    ");
        holder.NewsAuthor.setText("来源: "+junshiNews.getAuthor_name());
    }

    /**
     * 由于NewsItemAdapter继承自RecyclerView.Adapter,所以必须重写getItemCount
     * 告诉RecycleView一共有多少项
     * @return 返回数据源的长度
     */
    @Override
    public int getItemCount() {
        return mJunshiNewsList.size();
    }
}
