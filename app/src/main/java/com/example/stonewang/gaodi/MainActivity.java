package com.example.stonewang.gaodi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.stonewang.gaodi.LocalDBCreate.LocalDBCreate;
import com.example.stonewang.gaodi.db.AirforceDescribe;
import com.example.stonewang.gaodi.db.LandArmyDescribe;
import com.example.stonewang.gaodi.db.NavyDescribe;
import com.example.stonewang.gaodi.fragment.GuojiNewsFragment;
import com.example.stonewang.gaodi.fragment.LocalFragment;
import com.example.stonewang.gaodi.fragment.NewsItemFragment;
import com.example.stonewang.gaodi.util.JsonUtil;

import org.litepal.crud.DataSupport;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.id.message;
import static com.ashokvarma.bottomnavigation.BottomNavigationBar.BACKGROUND_STYLE_STATIC;

public class MainActivity extends AppCompatActivity {
    public int firstTimes=1;
    private DrawerLayout mDrawerLayout;
    private BottomNavigationBar bottom_navigation_bar;
    //数据库landArmy列表
    private List<LandArmyDescribe> landArmyDescribesList =new ArrayList<>();
    //数据库navyArmy列表
    private List<NavyDescribe> navyDescribesList =new ArrayList<>();
    //数据库Airforce列表
    private List<AirforceDescribe> airforceDescribesList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isNetworkAvailable(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setCheckedItem(R.id.nav_email);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_email:
                        Toast.makeText(MainActivity.this, "给这个邮箱发邮件吧", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                return true;
            }
        });

        init();//底部导航初始化
        initLocalDB();//初始化本地数据库

        //底部导航设置
        bottom_navigation_bar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_navigation_bar.setBackgroundStyle(BACKGROUND_STYLE_STATIC);
        //设置默认颜色
        bottom_navigation_bar
                .setInActiveColor(R.color.colorInActive)//设置未选中的Item的颜色，包括图片和文字
                .setActiveColor(R.color.colorActive)
                .setBarBackgroundColor(R.color.colorBarBg);//设置整个控件的背景色
        //添加选项
        bottom_navigation_bar.addItem(new BottomNavigationItem(R.drawable.ic_stat_new, "军事"))
                .addItem(new BottomNavigationItem(R.drawable.ic_stat_new, "国际"))
                .addItem(new BottomNavigationItem(R.drawable.ic_stat_read, "资料"))
                .initialise();
        bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                switch (position){
                    case 0:getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, new NewsItemFragment()).commit();
                        break;
                    case 1:getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment,new GuojiNewsFragment()).commit();
                        break;
                    case 2:getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, new LocalFragment()).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中

            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中

            }
        });

    }
    /**
     * 初始化本地数据库(本地资料页面数据)
     * 如果数据库存在则跳过，否者建立数据库
     */
    private void initLocalDB() {

        /**
         * 测试京东云API的返回数据
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://114.67.243.127/index.php/API/Test/test6")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将API返回的json数据传递给处理函数
                    JsonUtil jsonUtil = new JsonUtil();
                    jsonUtil.testAPI(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        /**军事
         * 开个线程，向聚合数据API，发起请求，并处理返回的数据
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问服务器地址
                            .url("http://v.juhe.cn/toutiao/index?type=junshi&key=3425b3b2cd4d3227f7455377f6276bab")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将服务器返回得到字符串传入处理函数
                    JsonUtil jsonUtil = new JsonUtil();
                    jsonUtil.parseJson(responseData);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        //陆军数据写入本地数据库
//        Log.d("ML01", "ML01 start");
        landArmyDescribesList = DataSupport.findAll(LandArmyDescribe.class);
        if (landArmyDescribesList.size() > 0){
            //创建成功，跳过
//            Log.d("ML01", "land existed"+" size="+landArmyDescribesList.size());
        }else{
            LocalDBCreate localDBCreate1 = new LocalDBCreate();
            localDBCreate1.CreatedLand();
//            Log.d("ML01", "land  created");
        }
        //海军数据写入本地数据库
        navyDescribesList = DataSupport.findAll(NavyDescribe.class);
        if (navyDescribesList.size()>0){
            //创建成功
        }else{
            LocalDBCreate localDBCreate2 = new LocalDBCreate();
            localDBCreate2.CreatedNavy();
        }
        //空军数据写入本地数据库
        airforceDescribesList = DataSupport.findAll(AirforceDescribe.class);
        if (airforceDescribesList.size()>0){
            //创建成功
        }else{
            LocalDBCreate localDBCreate3 = new LocalDBCreate();
            localDBCreate3.CreatedAirforce();
        }

        /**国际
         * 开个线程，向聚合数据API，发起请求，并处理返回的数据
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问服务器地址
                            .url("http://v.juhe.cn/toutiao/index?type=guoji&key=3425b3b2cd4d3227f7455377f6276bab")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //将服务器返回得到字符串传入处理函数
                    JsonUtil jsonUtil = new JsonUtil();
                    jsonUtil.parseJsonGuoji(responseData);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 底部导航初始化
     */
    private void init(){
        NewsItemFragment firstFragment = new NewsItemFragment();
        firstFragment.setArguments(getIntent().getExtras());
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.view_fragment, firstFragment).commit();
    }

    /**
     * 在onCreateOptionsMenu()方法中加载，toolbar.xml这个菜单文件
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * 处理各个menu按钮的点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.set:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            default:
                break;
        }
        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    Toast.makeText(context, "网络可用", Toast.LENGTH_SHORT).show();
                    // 当前所连接的网络可用
                    return true;
                }
            }else{
                Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

}
