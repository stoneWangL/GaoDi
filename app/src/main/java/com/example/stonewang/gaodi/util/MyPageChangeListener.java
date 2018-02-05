package com.example.stonewang.gaodi.util;

import android.support.v4.view.ViewPager;
import android.widget.Toast;

/**
 * Created by Lenovo on 2018/2/5.
 */

public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        这个方法会在屏幕滚动过程中不断被调用。
    }

    @Override
    public void onPageSelected(int position) {
        /**
         * 当用手指滑动翻页的时候，如果翻动成功了（滑动的距离够长），
         *
         * 手指抬起来就会立即执行这个方法，position就是当前滑动到的页面。
         */
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        /**
         * 这个方法在手指操作屏幕的时候发生变化。有三个值：0（END）,1(PRESS) , 2(UP)
         *
         * 当用手指滑动翻页时，手指按下去的时候会触发这个方法，state值为1
         *
         * 手指抬起时，如果发生了滑动（即使很小），这个值会变为2，然后最后变为0
         */
    }
}
