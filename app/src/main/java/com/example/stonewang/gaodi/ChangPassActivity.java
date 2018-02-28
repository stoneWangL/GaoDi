package com.example.stonewang.gaodi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.stonewang.gaodi.util.JsonUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lenovo on 2018/2/27.
 */

public class ChangPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changpass);

        Toolbar toolbar = (Toolbar) findViewById(R.id.change_pass);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();//在actionBar上增加一个返回按钮
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    /**
     * 确认原密码
     * @param view
     */
    public void AgreeFirst(View view){
        EditText editTextOne = (EditText)findViewById(R.id.changePass_textOne);
        final String oldPass = editTextOne.getText().toString();//旧密码
        SharedPreferences pref = getSharedPreferences("User",MODE_PRIVATE);
        final String username = pref.getString("userName", "");//用户名
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String url = "http://114.67.243.127/index.php/API/Api/checkLogin/user/"+username+"/password/"+oldPass;
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将API返回的json数据传递给处理函数
                    Log.d("stone11","登录返回数据:"+responseData);
                    JsonUtil jsonUtil = new JsonUtil();
                    int data = jsonUtil.parseJsonLogin(responseData);//传入处理函数后的返回数据
                    Message message = new Message();
                    message.what = data;
                    handlerAgreeFirst.sendMessage(message); //将Message对象发送出去
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 输入原密码正确，后续操作
     * @param view
     */
    public void AgreeNext(View view){

    }

    /**
     * 在actionBar上增加一个返回按钮
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Handler handlerAgreeFirst = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    new AlertDialog.Builder(ChangPassActivity.this)
                            .setTitle(R.string.error)
                            .setMessage(R.string.string_9)
                            .create().show();
                    break;
                case 3:
                case 2://在这里可以进行UI操作
                    LinearLayout firstLayout = (LinearLayout)findViewById(R.id.change_pass_first_layout);
                    LinearLayout nextLayout = (LinearLayout)findViewById(R.id.change_pass_next_layout);
                    firstLayout.setVisibility(View.GONE);
                    nextLayout.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };



}
