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
     * 判断新密码，以及提交新密码，返回修改结果
     * @param view
     */
    public void AgreeNext(View view){
        EditText editTextOne = (EditText) findViewById(R.id.changePass_newPass_one);
        EditText editTextTwo = (EditText) findViewById(R.id.changePass_newPass_two);
        String newPassOne = editTextOne.getText().toString();
        String newPAssTwo = editTextTwo.getText().toString();
        if (newPassOne.isEmpty() || newPAssTwo.isEmpty()){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.changePass)
                    .setMessage(R.string.string_11)
                    .setPositiveButton(R.string.agree,//确认
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create();
            dialog.show();
        }else if (!newPassOne.equals(newPAssTwo)){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.changePass)
                    .setMessage(R.string.string_2)
                    .setPositiveButton(R.string.agree,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create();
            dialog.show();
        }else if(newPassOne.length() < 6) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.changePass)
                    .setMessage(R.string.string_3)
                    .setPositiveButton(R.string.agree,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create();
            dialog.show();
        }else{
            SharedPreferences pref = getSharedPreferences("User",MODE_PRIVATE);
            final String username = pref.getString("userName", "");//用户名
            final String url = "http://114.67.243.127/index.php/API/Api/changePass/user/"+username+"/password/"+newPassOne;
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
                        //将API返回的json数据给handler
                        Message message = new Message();
                        message.what = Integer.parseInt(responseData);
                        handlerAgreeNext.sendMessage(message); //将Message对象发送出去,即修改的结果
                    }catch(Exception e){
                        e.printStackTrace();
                   }
                }
            }).start();
        }
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
                            .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    break;
                case 3:
                case 2://在这里可以进行UI操作，将输入原密码隐藏，将输入新密码显示
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

    private Handler handlerAgreeNext = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0://新密码与原密码相同
                    new AlertDialog.Builder(ChangPassActivity.this)
                            .setTitle(R.string.changePass)
                            .setMessage(R.string.string_12)
                            .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    break;
                case 1://修改成功
                    new AlertDialog.Builder(ChangPassActivity.this)
                            .setTitle(R.string.changePass)
                            .setMessage(R.string.success)
                            .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = getIntent();
                                    intent.setClass(ChangPassActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    ChangPassActivity.this.finish();
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    break;
                case 2://修改失败
                    new AlertDialog.Builder(ChangPassActivity.this)
                            .setTitle(R.string.changePass)
                            .setMessage(R.string.error)
                            .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    break;
                default:
                    break;
            }
        }
    };

}
