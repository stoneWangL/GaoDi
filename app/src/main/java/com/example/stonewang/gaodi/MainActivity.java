package com.example.stonewang.gaodi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.example.stonewang.gaodi.LocalDBCreate.LocalDBCreate;
import com.example.stonewang.gaodi.db.AirforceDescribe;
import com.example.stonewang.gaodi.db.Comment;
import com.example.stonewang.gaodi.db.GuojiNews;
import com.example.stonewang.gaodi.db.JunshiNews;
import com.example.stonewang.gaodi.db.LandArmyDescribe;
import com.example.stonewang.gaodi.db.NavyDescribe;
import com.example.stonewang.gaodi.fragment.GuojiNewsFragment;
import com.example.stonewang.gaodi.fragment.LocalFragment;
import com.example.stonewang.gaodi.fragment.JunshiNewsFragment;
import com.example.stonewang.gaodi.util.JsonUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.ashokvarma.bottomnavigation.BottomNavigationBar.BACKGROUND_STYLE_STATIC;
import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends AppCompatActivity {
    public int firstTimes=1;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    //下部导航栏
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
        Log.d("stone00","onCreate");

        SharedPreferences pref = getSharedPreferences("User",MODE_PRIVATE);
//        String url = pref.getString("userImage", "");
        String usernameText = pref.getString("userName", "");
        Boolean notGuest = pref.getBoolean("notGuest",false);
        String sex = pref.getString("sex", "男");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);

        View handerView = navView.getHeaderView(0);
        ImageView userImage = (ImageView) handerView.findViewById(R.id.nav_user_image);
        TextView username = (TextView) handerView.findViewById(R.id.nav_user_name);
        if (notGuest){
//            加载网络图片
//            Glide.with(this).load(url).into(userImage);
            if (sex.equals("男")){
                Glide.with(this).load(R.drawable.man).into(userImage);
            }else if (sex.equals("女")){
                Glide.with(this).load(R.drawable.female).into(userImage);
            }else{
                //挂上游客头像
                Glide.with(this).load(R.drawable.guest).into(userImage);
            }
        }else{
            Glide.with(this).load(R.drawable.guest).into(userImage);
        }

        username.setText("欢迎："+usernameText);





        navView.setCheckedItem(R.id.nav_color);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_change_pass:
                        //检查是否游客登录的凭证
                        SharedPreferences pref = getSharedPreferences("User",MODE_PRIVATE);
                        Boolean notGuest = pref.getBoolean("notGuest",false);
                        if(notGuest){
                            Intent intent = getIntent();
                            intent.setClass(MainActivity.this,ChangPassActivity.class);
                            startActivity(intent);
                        }else{
                            //游客登录，没有权限评论
                            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(R.string.string_7)
                                    .setMessage(R.string.pleaseLogin)
                                    .setPositiveButton(R.string.login,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = getIntent();
                                                    intent.setClass(MainActivity.this,LoginActivity.class);
                                                    startActivity(intent);
                                                    MainActivity.this.finish();
                                                }
                                            })
                                    .setNegativeButton(R.string.loginNot,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                    .create();
                            dialog.show();
                        }

                        break;
                    case R.id.nav_color:
                        Toast.makeText(MainActivity.this, "设置颜色", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        init();//底部导航初始化
        isNetworkAvailable(this);//查看网络是否可用
        initLocalDB();//初始化本地数据库

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("stone00","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("stone00","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("stone00","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("stone00","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("stone00","onDestroy");

//        DataSupport.deleteAll(JunshiNews.class);
        DataSupport.where("id > ?","10").find(JunshiNews.class);
        Log.d("stone00","删除了JunshiNews.class");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("stone00","onRestart");

    }

    /**
     * 用于创建保存文件
     */
    public void saveNum(int num){
        SharedPreferences.Editor editor = getSharedPreferences("numPage",MODE_PRIVATE).edit();
        editor.putInt("num",num);
        editor.apply();
    }
    /**
     * 用于创建保存Guoji页面的请求的，页面数
     */
    public void saveGuojiNum(int guojinum){
        SharedPreferences.Editor editor = getSharedPreferences("numPage",MODE_PRIVATE).edit();
        editor.putInt("guojinum",guojinum);
        editor.apply();
    }

    /**
     * 初始化数据库（新闻、评论、本地数据库）
     * 如果本地数据库存在则跳过，否者建立数据库
     *
     */
    private void initLocalDB() {


        /**
         * 京东云API_Junshi的返回结果
         * http://114.67.243.127/index.php/API/Api/junshiTest/number/1
         */
        if (DataSupport.findAll(JunshiNews.class).size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://114.67.243.127/index.php/API/Api/junshiTest/number/"+firstTimes)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        //将API返回的json数据传递给处理函数
                        JsonUtil jsonUtil = new JsonUtil();
                        jsonUtil.parseJsonJunshi(responseData);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        int num = 0;
        num = DataSupport.findAll(JunshiNews.class).size();
        Log.d("stone00","JunshiNews->size的大小是"+num);
        if (DataSupport.findAll(JunshiNews.class).size()==0 | DataSupport.findAll(JunshiNews.class).size() >10){
            num = firstTimes+1;
        }else{
            num = (DataSupport.findAll(JunshiNews.class).size()/10)+1;
        }
        saveNum(num);//记录number=2,即已經請求了1頁，下次請求2頁

        /**
         * 国际
         * 京东云API->Guoji的返回结果
         */
        if (DataSupport.findAll(GuojiNews.class).size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://114.67.243.127/index.php/API/Api/guojiTest/number/"+firstTimes)
                                .build();

                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        //将API返回的json数据传递给处理函数
                        JsonUtil jsonUtil = new JsonUtil();
                        jsonUtil.parseJsonGuoji(responseData);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        int guojinum = 0;
        guojinum = DataSupport.findAll(GuojiNews.class).size();
        Log.d("stone00","GuojiNews->size的大小是"+guojinum);
        if (guojinum==0 | guojinum >8){
            guojinum = firstTimes+1;
        }else{
            guojinum = (guojinum/10)+1;
        }
        saveGuojiNum(guojinum);//记录number=2,即已經請求了1頁，下次請求2頁

        /**
         * 评论
         * 京东云API->Comment的返回结果
         */
        if(DataSupport.findAll(Comment.class).size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://114.67.243.127/index.php/API/Api/findAllComment")
                                .build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        //将服务器返回得到字符串传入处理函数

                        if (responseData.equals("0")){
                            Log.d("stone006","返回数据为空");
                        }else{
                            DataSupport.deleteAll(Comment.class);//先清空Comment本地数据库
                            Log.d("stone006",responseData);
                            JsonUtil jsonUtil = new JsonUtil();
                            jsonUtil.parseJsonComment(responseData);//将返回的数据按UTF-8编码后，解析存入本地数据库Comment
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }



        //陆军数据写入本地数据库
        landArmyDescribesList = DataSupport.findAll(LandArmyDescribe.class);
        if (landArmyDescribesList.size() > 0){
            //创建成功
        }else{
            LocalDBCreate localDBCreate1 = new LocalDBCreate();
            localDBCreate1.CreatedLand();
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
    }

    /**
     * 底部导航初始化
     */
    private void init(){
        JunshiNewsFragment firstFragment = new JunshiNewsFragment();
        firstFragment.setArguments(getIntent().getExtras());
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.view_fragment, firstFragment).commit();

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
                    case 0:getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, new JunshiNewsFragment()).commit();
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

    /**
     * 查看网络是否可用
     * @param context
     * @return
     */
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
