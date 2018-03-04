package com.example.stonewang.gaodi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.stonewang.gaodi.db.Comment;
import com.example.stonewang.gaodi.db.GuojiNews;
import com.example.stonewang.gaodi.db.JunshiNews;

import org.litepal.crud.DataSupport;

/**
 * Created by StoneWang on 2018/2/3.
 */

public class FirstActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        delete_Message();
        delete_DataBase();
        //延迟一段时间后跳转到另一个界面
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent intent = new Intent (FirstActivity.this,LoginActivity.class);
                startActivity(intent);//跳转界面
                FirstActivity.this.finish();//关闭此界面
            }
        }, 1000);

    }
    public void delete_Message(){
        SharedPreferences.Editor editor = getSharedPreferences("User",MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
    //清空之前的数据库
    public void delete_DataBase(){
        DataSupport.deleteAll(JunshiNews.class);
        DataSupport.deleteAll(GuojiNews.class);
        DataSupport.deleteAll(Comment.class);
//        DataSupport.where("id > ?","10").find(JunshiNews.class);
//        Log.d("stone00","删除了JunshiNews.class");
    }


}
