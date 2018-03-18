package com.example.stonewang.gaodi.LocalShowActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.stonewang.gaodi.R;

/**
 * Created by stoneWang on 2017/4/9.
 */

public class LandShowOneActivity extends AppCompatActivity {
    private boolean isVisible1 = true;
    private boolean isVisible2 = true;
    private boolean isVisible3 = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_show_one);
        View cv = getWindow().getDecorView();//获取当前Activity的View

        //自定义toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_land_show);
        setSupportActionBar(toolbar);

        //设置返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //通过Intent获取传过来的内容,来填充
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");//武器名
        int imageId = intent.getIntExtra("imageId",0);//图片
        String baseParameter = intent.getStringExtra("baseParameter");//基本参数
        String historicalBackground = intent.getStringExtra("historicalBackground");//研发历史背景
        String detailedInstroduction = intent.getStringExtra("detailedInstroduction");//详细介绍
        String sumUp = intent.getStringExtra("sumUp");//总结

        //初始化并填充 武器名,图片
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_land_show);
        ImageView landImageView = (ImageView) findViewById(R.id.show_land_image_view);
        collapsingToolbar.setTitle(name);
        Glide.with(this).load(imageId).into(landImageView);



        //基本参数(隐藏/显示)
        LinearLayout onOff1 = (LinearLayout) findViewById(R.id.show_on_off_1);
        final RelativeLayout contentOnOff1 = (RelativeLayout) findViewById(R.id.show_content_on_off_1);
        TextView landContentText1 = (TextView) findViewById(R.id.show_content_text_1);
        landContentText1.setText(baseParameter);

        contentOnOff1.setVisibility(cv.GONE);
        onOff1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                if (isVisible1) {
                    message.what = 0;
                    isVisible1 = false;
                    contentOnOff1.setVisibility(View.VISIBLE);//这一句显示布局RelativeLayout区域
                } else {
                    message.what = 1;
                    contentOnOff1.setVisibility(View.GONE);//这一句即隐藏布局RelativeLayout区域
                    isVisible1 = true;
                }
                unfold01Next.sendMessage(message);
            }
        });

        //历史背景(隐藏/显示)
        LinearLayout onOff2 = (LinearLayout) findViewById(R.id.show_on_off_2);
        final RelativeLayout contentOnOff2 = (RelativeLayout) findViewById(R.id.show_content_on_off_2);
        TextView landContentText2 = (TextView) findViewById(R.id.show_content_text_2);
        landContentText2.setText(historicalBackground);

        contentOnOff2.setVisibility(cv.GONE);
        onOff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                if (isVisible2) {
                    message.what = 0;
                    isVisible2 = false;
                    contentOnOff2.setVisibility(View.VISIBLE);//这一句显示布局RelativeLayout区域
                } else {
                    message.what = 1;
                    contentOnOff2.setVisibility(View.GONE);//这一句即隐藏布局RelativeLayout区域
                    isVisible2 = true;
                }
                unfold02Next.sendMessage(message);
            }
        });

        //详细介绍(隐藏/显示)
        LinearLayout onOff3 = (LinearLayout) findViewById(R.id.show_on_off_3);
        final RelativeLayout contentOnOff3 = (RelativeLayout) findViewById(R.id.show_content_on_off_3);
        TextView landContentText3 = (TextView) findViewById(R.id.show_content_text_3);
        landContentText3.setText(detailedInstroduction);

        contentOnOff3.setVisibility(cv.GONE);
        onOff3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                if (isVisible3) {
                    message.what = 0;
                    isVisible3 = false;
                    contentOnOff3.setVisibility(View.VISIBLE);//这一句显示布局RelativeLayout区域
                } else {
                    message.what = 1;
                    contentOnOff3.setVisibility(View.GONE);//这一句即隐藏布局RelativeLayout区域
                    isVisible3 = true;
                }
                unfold03Next.sendMessage(message);
            }
        });

        //初始化并填充 总结(显示)
        TextView landContentText4 = (TextView) findViewById(R.id.show_content_text_4);
        landContentText4.setText(sumUp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        return true;
    }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 分享功能
     * @param view
     */
    public void shareBtn(View view){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");//武器名
        String baseParameter = intent.getStringExtra("baseParameter");//基本参数
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, name + "\n基本参数\n"+ baseParameter );
        startActivity(Intent.createChooser(textIntent, "分享"));
    }

    /**
     * 展开，隐藏UI处理
     */
    private Handler unfold01Next= new Handler(){
        public void handleMessage(Message msg){
            ImageView imageView01 = (ImageView) findViewById(R.id.unfold_image01);
            switch (msg.what){
                case 1://展开->隐藏
                    imageView01.setImageResource(R.drawable.unfold_more);
                    break;
                case 0://隐藏->展开
                    imageView01.setImageResource(R.drawable.unfold_less);
                    break;
                default:
                    break;
            }
        }
    };
    private Handler unfold02Next= new Handler(){
        public void handleMessage(Message msg){
            ImageView imageView02 = (ImageView) findViewById(R.id.unfold_image02);
            switch (msg.what){
                case 1://展开->隐藏
                    imageView02.setImageResource(R.drawable.unfold_more);
                    break;
                case 0://隐藏->展开
                    imageView02.setImageResource(R.drawable.unfold_less);
                    break;
                default:
                    break;
            }
        }
    };
    private Handler unfold03Next= new Handler(){
        public void handleMessage(Message msg){
            ImageView imageView03 = (ImageView) findViewById(R.id.unfold_image03);
            switch (msg.what){
                case 1://展开->隐藏
                    imageView03.setImageResource(R.drawable.unfold_more);
                    break;
                case 0://隐藏->展开
                    imageView03.setImageResource(R.drawable.unfold_less);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("User",MODE_PRIVATE);
        String color = pref.getString("color","one");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_land_show);

        if (color.equals("two")){
            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorBarBg2));
        }else{
            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorBarBg));
        }
    }
}
