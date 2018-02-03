package com.example.stonewang.gaodi;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by StoneWang on 2018/2/3.
 */

public class FirstActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

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

}
