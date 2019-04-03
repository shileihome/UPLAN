package com.uplan.miyao.base.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uplan.miyao.app.UPLANApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView-BaseAdapter
 */
public abstract class BaseListAdapter extends BaseAdapter {

    private List mList;

    /** 用于记录点击的 Item 的 position，是控制 item 展开的核心 */
    private int currentItem = -1;


    public BaseListAdapter() {
        mList = new ArrayList();
    }

    /**
     * 返回ListView的数据
     *
     * @return list数据
     */
    public List getData() {
        return this.mList;
    }


    /**
     * 刷新ListView的adapter
     */
    protected void invalidated() {
        notifyDataSetInvalidated();
    }

    /**
     * 设置 ListView Type
     *
     * @param position item位置
     * @return 类型
     */
    protected int getAbsItemViewType(int position) {
        return 0;
    }

    /**
     * 设置 ListView Type Count
     *
     * @return ListView type 数量
     */
    protected int getAbsViewTypeCount() {
        return 1;
    }

    /**
     * 根据item type设置item
     *
     * @param type item type类型
     * @return item
     */
    protected AbsAdapterItem getAbsAdapterItem(int type) {
        return null;
    }

    /**
     * 设置item
     *
     * @return item
     */
    protected abstract AbsAdapterItem getAbsAdapterItem();

    public void setList(List list) {
        this.mList = list;
    }

    public void addList(List list) {
        this.mList.addAll(list);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > getCount() - 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getAbsItemViewType(position);
    }


    @Override
    public int getViewTypeCount() {
        return getAbsViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AbsAdapterItem item = null;
        if (convertView == null) {
            int count = getViewTypeCount();
            if (count != 1) {
                int type = getItemViewType(position);
                item = getAbsAdapterItem(type);
            } else {
                item = getAbsAdapterItem();
            }
            convertView = LayoutInflater
                    .from(getContext()).inflate(item.getItemLayout(), null, false);
            item.init(convertView);
            convertView.setTag(item);
        }
        if (item == null) {
            item = (AbsAdapterItem) convertView.getTag();
        }
        item.bindData(mList.get(position));
        return convertView;

    }

    private Context getContext() {
        return UPLANApplication.getContext();
    }


}
