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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.stonewang.gaodi.util.JsonUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lenovo on 2018/2/3.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText mUserName = null;
    private EditText mPassword = null;
    ProgressDialog mDialog;//对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = (EditText)findViewById(R.id.user_name);
        mPassword = (EditText)findViewById(R.id.password);

    }

    /**
     * 游客登录按钮点击后
     * @param view
     */
    public void guest_button(View view){

        saveGuestLogin();//保存本地用户，到文件
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "游客身份，登录成功", Toast.LENGTH_SHORT).show();
        LoginActivity.this.finish();
    }

    /**
     * 用户登录
     * @param view
     */
    public void login_button(View view){

        final String user = mUserName.getText().toString();
        final String pass = mPassword.getText().toString();

        if(user.isEmpty() || pass.isEmpty() ){//判断是否输入相关值
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("登录")
                    .setMessage("请输入用户名/密码")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create();
            dialog.show();
        }else{
            final String url = "http://114.67.243.127/index.php/API/Api/checkLogin/user/"+user+"/password/"+pass;
            //http://114.67.243.127/index.php/API/Api/checkLogin/user/stone/password/123456
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
                        JsonUtil jsonUtil = new JsonUtil();
                        int data = jsonUtil.parseJsonLogin(responseData);//传入处理函数
                        if (data == 2 || data == 3){
                            saveUser(user,data);
                        }
                        Log.d("stone666","返回的数字："+data);
                        Message message = new Message();
                        message.what = data;
                        login_next.sendMessage(message); //将Message对象发送出去
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }


    private Handler login_next = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle(R.string.errorLogin)
                            .setMessage(R.string.string_10)
                            .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    break;
                case 3:
                case 2://在这里可以进行UI操作
                    mDialog = ProgressDialog.show(LoginActivity.this,"请等待...", "正在登录...", true);
                    new Thread(){
                        public void run(){
                            try{
                                SystemClock.sleep(1500);
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                mDialog.dismiss();
                            }
                        }
                    }.start();
                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 注册
     * @param view
     */
    public void register_button(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 保存用户名
     * @param username
     */
    public void saveUser(String username,int num){
        SharedPreferences.Editor editor = getSharedPreferences("User",MODE_PRIVATE).edit();
        editor.putBoolean("notGuest",true);
        editor.putString("userName",username);
        if (num == 2){
            editor.putString("sex","男");
        }else{
            editor.putString("sex","女");
        }
        editor.apply();
    }

    /**
     * 保存本地用户
     */
    public void saveGuestLogin(){
        SharedPreferences.Editor editor = getSharedPreferences("User",MODE_PRIVATE).edit();
        editor.putBoolean("notGuest",false);
        editor.putString("userName","游客");
        editor.apply();
    }



}
