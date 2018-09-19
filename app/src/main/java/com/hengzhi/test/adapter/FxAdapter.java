package com.hengzhi.test.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class FxAdapter extends FragmentPagerAdapter {

  List<Pair<String, Fragment>> mList;

  public FxAdapter(FragmentManager fm, List<Pair<String, Fragment>> items) {
    super(fm);
    mList = items;
  }

  @Override
  public Fragment getItem(int position) {
    return mList.get(position).second;
  }

  @Override
  public int getCount() {
    return mList.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mList.get(position).first;
  }
}
