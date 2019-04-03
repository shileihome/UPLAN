package com.uplan.miyao.base.list;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uplan.miyao.R;
import com.uplan.miyao.base.UiUtils;
import com.uplan.miyao.base.mvp.BaseActivity;
import com.uplan.miyao.base.mvp.IPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseListActivity
 */
public abstract class BaseListActivity<P extends IPresenter> extends BaseActivity<P> implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    /** 拖动类型：下拉刷新 */
    public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;

    /** 拖动类型：上拉加载 */
    public static final int MODE_PULL_UP_TO_LOAD_MORE = 0x2;

    /** 拖动类型：下拉刷新 && 上拉加载 */
    public static final int MODE_BOTH = 0x3;

    /** 拖动类型：无拖动 */
    public static final int MODE_NONE = 0x4;

    /** 默认布局 */
    public static final int NO_LAYOUT = 0;

    /** 默认页数 */
    public static final int DEFAULT_PAGE_INDEX = -1;

    /** 刷新组件 */
    private SmartRefreshLayout mRefreshLayout;

    /** ListAdapter */
    private ListAdapter mListAdapter;


    @Override
    protected int getContentLayout() {
        return R.layout.base_list_activity;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        LinearLayout llTop = (LinearLayout) contentView.findViewById(R.id.ll_top);
        LinearLayout llBottom = (LinearLayout) contentView.findViewById(R.id.ll_bottom);
        if (getExtraTopLayout() != NO_LAYOUT) {
            View extraView = UiUtils.inflateView(getExtraTopLayout());
            llTop.addView(extraView, 0);
        }

        if (getExtraBottomLayout() != NO_LAYOUT) {
            View extraView = UiUtils.inflateView(getExtraBottomLayout());
            onInitBottom(extraView);
            llBottom.addView(extraView, 0);
        }

        ListView listView = (ListView) contentView.findViewById(R.id.list_view);
        mRefreshLayout = (SmartRefreshLayout) contentView.findViewById(R.id.refresh_layout);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        initRefreshListener();

        if (getHeaderLayout() != NO_LAYOUT) {
            View headerView = UiUtils.inflateView(getHeaderLayout());
            onInitHeader(headerView);
            listView.addHeaderView(headerView);
        }

        if (getFooterLayout() != NO_LAYOUT) {
            View footerView = UiUtils.inflateView(getFooterLayout());
            onInitFooter(footerView);
            listView.addFooterView(footerView);
        }
        addHeaderView();
        mListAdapter = new ListAdapter();
        listView.setAdapter(mListAdapter);

        init(savedInstanceState);
    }


    /**
     * 初始化刷新控件
     */
    private void initRefreshListener() {

        //未满一页时不显示开启上拉加载功能
        //mRefreshLayout.setEnableLoadmoreWhenContentNotFull(true);

        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BaseListActivity.this.onRefresh();
            }
        });

        //上拉加载更多
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                BaseListActivity.this.onLoadMore();
            }
        });

        setRefreshMode(MODE_NONE);
    }

    /**
     * 刷新拖动类型
     */
    public void setRefreshMode(int mode) {
        if(mRefreshLayout == null){
            return;
        }

        if (mode != MODE_NONE) {
            if (mode == MODE_BOTH || mode == MODE_PULL_DOWN_TO_REFRESH) {
                // 下拉刷新
                mRefreshLayout.setEnableRefresh(true);
            } else {
                // 下拉刷新不可用
                mRefreshLayout.setEnableRefresh(false);
            }
            if (mode == MODE_BOTH || mode == MODE_PULL_UP_TO_LOAD_MORE) {
                // 上拉加载
                mRefreshLayout.setEnableLoadmore(true);
            } else {
                //上拉加载不可用
                mRefreshLayout.setEnableLoadmore(false);
            }
        } else {
            //上拉加载 下拉刷新都不可用
            mRefreshLayout.setEnableLoadmore(false);
            mRefreshLayout.setEnableRefresh(false);
        }
    }

    /**
     * 结束页面滑动
     */
    public void pullDone(){
        if(mRefreshLayout != null){
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadmore();
        }
    }

    /**
     * 添加头
     */
    protected void addHeaderView() {
    }

    /**
     * 上拉刷新
     */
    protected abstract void onRefresh();


    /**
     * 下拉加载更多
     */
    protected abstract void onLoadMore();

    /**
     * 设置额外的上面布局
     *
     * @return ExtraLayoutId
     */
    protected int getExtraTopLayout() {
        return 0;
    }

    /**
     * 设置额外的底部布局
     *
     * @return ExtraLayoutId
     */
    protected int getExtraBottomLayout() {
        return 0;
    }

    /**
     * 设置ListView header 布局
     *
     * @return HeaderLayoutId
     */
    protected int getHeaderLayout(){
        return 0;
    }

    /**
     * 设置ListView footer 布局
     *
     * @return FooterLayoutId
     */
    protected int getFooterLayout(){
        return 0;
    }

    /**
     * Header初始化操作
     *
     * @param view Header
     */
    protected void onInitHeader(View view) {}

    /**
     * Footer初始化操作
     *
     * @param view Footer
     */
    protected void onInitFooter(View view) {}

    /**
     * Bottom初始化操作
     *
     * @param view bottom
     */

    protected void onInitBottom(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    /**
     * 初始化操作
     *
     * @param savedInstanceState Bundle
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * ListView adapter 非空检查
     *
     * @return boolean
     */
    private boolean isAdapterNotNull() {
        return mListAdapter != null;
    }

    /**
     * 为 ListView adapter 设置值 初次设置或者更新加载数据
     * ListView 下拉刷新时 需要调用此方法
     *
     * @param list 列表数据
     */
    protected void setData(List list) {
        if (isAdapterNotNull()) {
            mListAdapter.setList(list);
            upData();
        }
    }

    /**
     * ListView 加载更多数据时 需要调用此方法
     *
     * @param list 列表数据
     */
    protected void addData(List list) {
        if (isAdapterNotNull()) {
            mListAdapter.addList(list);
            upData();
        }
    }

    /**
     * ListView 加载更多数据时 需要调用此方法
     *
     * @param list 列表数据
     */
    protected void addData(int index, List list) {
        if (isAdapterNotNull()) {
            mListAdapter.addList(index, list);
            upData();
        }
    }

    /**
     * 返回ListView 当前数据
     *
     * @return 列表数据
     */
    protected List getData() {
        if (isAdapterNotNull()) {
            return mListAdapter.mList;
        } else {
            return null;
        }
    }

    /**
     * 刷新ListView adapter
     */
    protected void invalidated() {
        if (isAdapterNotNull()) {
            mListAdapter.notifyDataSetInvalidated();
        }
    }

    /**
     * 更新ListView adapter
     */
    protected void upData() {
        if (isAdapterNotNull()) {
            mListAdapter.notifyDataSetChanged();
            int status = getPageStatus();
            if (status == PAGE_STATUS_SUCCESS && getData().isEmpty()) {
                setPageStatus(PAGE_STATUS_EMPTY);
            } else if (status != PAGE_STATUS_SUCCESS && !getData().isEmpty()) {
                setPageStatus(PAGE_STATUS_SUCCESS);
            }
        }
    }

    /**
     * 设置 ListView type
     */
    protected int getAbsItemViewType(int position) {
        return 0;
    }

    /**
     * 设置ListView type count
     */

    protected int getAbsViewTypeCount() {
        return 1;
    }

    /**
     * 设置 ListView Item
     *
     * @return AbsAdapterItem
     */
    protected AbsAdapterItem getAbsAdapterItem(int type) {
        return null;
    }

    /**
     * 具体Item
     *
     * @return AbsAdapterItem
     */
    protected abstract AbsAdapterItem getAbsAdapterItem();

    private class ListAdapter extends BaseAdapter {
        private List mList;

        private ListAdapter() {
            mList = new ArrayList();
        }

        private void setList(List list) {
            mList = list;
        }

        /**
         * 加载更多数据
         *
         * @param list 列表数据
         */
        private void addList(List list) {
            mList.addAll(list);
        }

        /**
         * 加载更多数据
         *
         * @param list 列表数据
         */
        private void addList(int index, List list) {
            mList.addAll(index, list);
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
                convertView = UiUtils.inflateView(item.getItemLayout());
                item.init(convertView);
                convertView.setTag(item);
            }
            if (item == null) {
                item = (AbsAdapterItem) convertView.getTag();
            }
            item.bindData(mList.get(position));
            return convertView;
        }
    }

}
