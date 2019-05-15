package com.uplan.miyao.ui.financial.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: Created by shilei on 2019/5/15-10:42
 * Description:
 */
public class CenterViewPageAdapter extends PagerAdapter {
    private List<View> views;
    private Context context;

    //有参构造
    public CenterViewPageAdapter(List<View> views, Context context) {
        super();
        this.views = views;
        this.context = context;
    }
    //获得长度
    @Override
    public int getCount() {

        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0==arg1;
    }
    //展示的view
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //获得展示的view
        View view=views.get(position);
        //添加到容器
        container.addView(view);
        //返回显示的view
        return view;
    }
    //销毁view
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //从容器中移除view
        container.removeView((View) object);
    }

}
