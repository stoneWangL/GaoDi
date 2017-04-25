package com.example.stonewang.gaodi;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.stonewang.gaodi.db.LandArmyDescribe;
import com.example.stonewang.gaodi.db.NavyDescribe;
import com.example.stonewang.gaodi.fragment.Fragment3;
import com.example.stonewang.gaodi.fragment.LocalFragment;
import com.example.stonewang.gaodi.fragment.NewsItemFragment;
import com.example.stonewang.gaodi.util.LocalDBCreate;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

import static com.ashokvarma.bottomnavigation.BottomNavigationBar.BACKGROUND_STYLE_STATIC;

public class MainActivity extends AppCompatActivity {
    public int firstTimes=1;

    private BottomNavigationBar bottom_navigation_bar;
    //数据库landArmy列表
    private List<LandArmyDescribe> landArmyDescribesList =new ArrayList<>();
    //数据库navyArmy列表
    private List<NavyDescribe> navyDescribesList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        init();//底部导航初始化
        initLocalDB();//初始化本地数据库
        bottom_navigation_bar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_navigation_bar.setBackgroundStyle(BACKGROUND_STYLE_STATIC);
        //设置默认颜色
        bottom_navigation_bar
                .setInActiveColor(R.color.colorInActive)//设置未选中的Item的颜色，包括图片和文字
                .setActiveColor(R.color.colorActive)
                .setBarBackgroundColor(R.color.colorBarBg);//设置整个控件的背景色
        //添加选项
        bottom_navigation_bar.addItem(new BottomNavigationItem(R.drawable.ic_stat_new, "新闻"))
                .addItem(new BottomNavigationItem(R.drawable.ic_stat_read, "未定"))
                .addItem(new BottomNavigationItem(R.drawable.ic_stat_read, "资料"))
                .initialise();
        bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                switch (position){
                    case 0:getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment, new NewsItemFragment()).commit();
                        break;
                    case 1:getSupportFragmentManager().beginTransaction().replace(R.id.view_fragment,new Fragment3()).commit();
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

        Log.d("ML01", "ML01 start");

        landArmyDescribesList = DataSupport.findAll(LandArmyDescribe.class);
        if (landArmyDescribesList.size() > 0){
            //创建成功，跳过
//            Log.d("ML01", "land existed"+" size="+landArmyDescribesList.size());
        }else{
            LocalDBCreate localDBCreate1 = new LocalDBCreate();
            localDBCreate1.CreatedLand();
//            Log.d("ML01", "land  created");
        }

        navyDescribesList = DataSupport.findAll(NavyDescribe.class);
        if (navyDescribesList.size()>0){
            //创建成功
//            Log.d("ML01", "navy existed"+" size="+navyDescribesList.size());
        }else{
            LocalDBCreate localDBCreate2 = new LocalDBCreate();
            localDBCreate2.CreatedNavy();
//            Log.d("ML01", "navy  created");
        }

//        Log.d("ML01", "land and navy success");
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
                Toast.makeText(this, "你点击了Set按钮", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

}
