package com.example.stonewang.gaodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by stoneWang on 2017/4/9.
 */

public class LandShowOneActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_show_one);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String historicalBackground = intent.getStringExtra("historicalBackground");
        String imageId = intent.getIntExtra("imageId");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_land_show);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_land_show);
        ImageView landImageView = (ImageView) findViewById(R.id.show_land_image_view);
        TextView landContentText = (TextView) findViewById(R.id.land_show_content_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//此处设置返回按钮
        }
        collapsingToolbar.setTitle(name);
        Glide.with(this).load(imageId).into(landImageView);
        landContentText.setText(historicalBackground);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
