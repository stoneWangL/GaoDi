package com.example.stonewang.gaodi.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.stonewang.gaodi.JunshiNewsActivity;
import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.Comment;
import com.example.stonewang.gaodi.db.JunshiNews;
import com.example.stonewang.gaodi.fragment.NewsPageFragment;
import com.example.stonewang.gaodi.util.JsonUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Thread.getDefaultUncaughtExceptionHandler;
import static java.lang.Thread.sleep;

/**
 * Created by stoneWang on 2017/3/12.
 * fragment页面的数据由Adapter来设置
 * RecyclerView 的所有点击事件交给View去注册
 *
 */
public class JunshiItemAdapter extends RecyclerView.Adapter<JunshiItemAdapter.ViewHolder>{
    private Context mContext;
    private List<JunshiNews> mJunshiNewsList;//全局变量mJunshiNewsesList
    private List<Comment> commentList = new ArrayList<>(), temp = new ArrayList<>();//评论

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
                Intent intent = new Intent(v.getContext(), JunshiNewsActivity.class);
                intent.putExtra("NewsUrl", junshiNews.getUrl());
                intent.putExtra("NewsTitle",junshiNews.getTitle());
                intent.putExtra("NewsId",junshiNews.getNewsid());
                intent.putExtra("News","junshi");
                v.getContext().startActivity(intent);
//                RequestComment(junshiNews.getNewsid(),"junshi");
            }
        });
        return holder;//返回ViewHolder实例
    }
    /**
     * 请求评论数据
     */
//    private void RequestComment(int newsid,String news){
//
//        //http://114.67.243.127/index.php/API/Api/findComment/news/junshi/newsid/5420
//        final String url = "http://114.67.243.127/index.php/API/Api/findComment/news/"+news+"/newsid/"+newsid;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            //指定访问服务器地址
//                            .url(url)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
//                    //将服务器返回得到字符串传入处理函数
//
//                    if (responseData.equals("0")){
//                        Log.d("stone006","返回数据为空");
//                    }else{
//                        DataSupport.deleteAll(Comment.class);//先清空Comment本地数据库
//                        Log.d("stone006",responseData);
//                        JsonUtil jsonUtil = new JsonUtil();
//                        jsonUtil.parseJsonComment(responseData);//将返回的数据按UTF-8编码后，解析存入本地数据库Comment
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }

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
