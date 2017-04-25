package com.example.stonewang.gaodi.LocalShowActivity;

import android.content.Intent;
import android.os.Bundle;
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
 * Created by stoneWang on 2017/4/25.
 */

public class AirforceShowOneActivity extends AppCompatActivity {
    private boolean isVisible1 = true;
    private boolean isVisible2 = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_show_one);
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



        //初始化并填充 基本参数(显示)
        TextView landContentText = (TextView) findViewById(R.id.show_content_text_1);
        landContentText.setText(baseParameter);

        //初始化并填充 研发历史背景(隐藏/显示)
        LinearLayout onOff1 = (LinearLayout) findViewById(R.id.show_on_off_1);
        final RelativeLayout contentOnOff1 = (RelativeLayout) findViewById(R.id.show_content_on_off_1);
        TextView landContentText2 = (TextView) findViewById(R.id.show_content_text_2);
        landContentText2.setText(historicalBackground);

        contentOnOff1.setVisibility(cv.GONE);
        onOff1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible1) {
                    isVisible1 = false;
                    contentOnOff1.setVisibility(View.VISIBLE);//这一句显示布局RelativeLayout区域
                } else {
                    contentOnOff1.setVisibility(View.GONE);//这一句即隐藏布局RelativeLayout区域
                    isVisible1 = true;
                }
            }
        });

        //详细介绍(隐藏/显示)
        LinearLayout onOff2 = (LinearLayout) findViewById(R.id.show_on_off_2);
        final RelativeLayout contentOnOff2 = (RelativeLayout) findViewById(R.id.show_content_on_off_2);
        TextView landContentText3 = (TextView) findViewById(R.id.show_content_text_3);
        landContentText3.setText(detailedInstroduction);

        contentOnOff2.setVisibility(cv.GONE);
        onOff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible2) {
                    isVisible2 = false;
                    contentOnOff2.setVisibility(View.VISIBLE);//这一句显示布局RelativeLayout区域
                } else {
                    contentOnOff2.setVisibility(View.GONE);//这一句即隐藏布局RelativeLayout区域
                    isVisible2 = true;
                }
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
}
