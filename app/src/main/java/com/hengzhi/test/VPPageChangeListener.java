package com.hengzhi.test;

import android.support.v4.view.ViewPager;

/**
 * 作者:张磊
 * 日期:2018/9/19
 * 说明:
 */
public abstract class VPPageChangeListener implements ViewPager.OnPageChangeListener {


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public abstract void onPageSelected(int position);

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
