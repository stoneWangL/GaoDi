package com.example.stonewang.gaodi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lenovo on 2018/2/7.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordNext;

    ProgressDialog mDialog;//对话框


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = (EditText) findViewById(R.id.re_username);
        mPassword = (EditText) findViewById(R.id.re_password);
        mPasswordNext = (EditText) findViewById(R.id.re_password_next);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register);
        toolbar.setTitle(R.string.register);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void agree_button(View view){
        final String userName = mUsername.getText().toString();
        final String passWord = mPassword.getText().toString();
        final String passWordNext = mPasswordNext.getText().toString();
        if (userName.isEmpty() || passWord.isEmpty() || passWordNext.isEmpty()){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.register)//注册
                    .setMessage(R.string.string_1)//请输入用户名/密码/确认密码
                    .setPositiveButton(R.string.agree,//确认
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create();
            dialog.show();
        }else if (!passWord.equals(passWordNext)){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.register)//注册
                    .setMessage(R.string.string_2)//前后密码不一致
                    .setPositiveButton(R.string.agree,//确认
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create();
            dialog.show();
        }else if(passWord.length() < 6) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.register)//注册
                    .setMessage(R.string.string_3)//密码长度为6-12位
                    .setPositiveButton(R.string.agree,//确认
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create();
            dialog.show();
        }else{
            final String url = "http://114.67.243.127/index.php/API/Api/register/user/"+userName+"/password/"+passWord;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        //将API返回的json数据传递给处理函数
                        Log.d("stone11","登录返回数据:"+responseData);
                        if (responseData.equals('1')){
                            saveUser(userName);
                        }
                        Message message = new Message();
                        message.what = Integer.parseInt(responseData);
                        agree_next.sendMessage(message); //将Message对象发送出去
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    private Handler agree_next = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1://在这里可以进行UI操作
                    mDialog = ProgressDialog.show(RegisterActivity.this,"请等待...", "注册成功，正在登录...", true);
                    new Thread(){
                        public void run(){
                            try{
                                SystemClock.sleep(1500);

                                Intent intent = new Intent();
                                intent.setClass(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                                RegisterActivity.this.finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                mDialog.dismiss();
                            }
                        }
                    }.start();
                    break;
                case 2:
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("注册失败")
                            .setMessage("用户名重复")
                            .create().show();
                    break;
                case 3:
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("注册失败")
                            .setMessage("注册失败")
                            .create().show();
                    break;
                default:
                    break;
            }
        }
    };


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
     * 保存用户名
     * @param username
     */
    public void saveUser(String username){
        SharedPreferences.Editor editor = getSharedPreferences("User",MODE_PRIVATE).edit();
        editor.putString("userName",username);
        editor.apply();
    }
}
