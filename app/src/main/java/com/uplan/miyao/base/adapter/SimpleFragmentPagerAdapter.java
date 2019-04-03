package com.uplan.miyao.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Author: Created by fangmingdong on 2018/4/18-下午1:28
 * Description:
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mTabs;
    private String[] mTabTitles;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Fragment[] tabs, String[] tabtitles) {
        super(fm);
        mTabs = tabs;
        mTabTitles = tabtitles;
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, Fragment[] tabs) {
        super(fm);
        mTabs = tabs;
        mTabTitles = new String[tabs.length];
    }

    @Override
    public Fragment getItem(int position) {
        return mTabs[position];
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    public void clear() {
        mTabs = new Fragment[0];
        mTabTitles = new String[0];
        notifyDataSetChanged();
    }
}
