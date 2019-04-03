package com.uplan.miyao.base.recycler;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.base.mvp.IPresenter;
import com.uplan.miyao.base.recycler.adapter.BaseSimpleRVAdapter;

/**
 * BaseListFragment
 */
public abstract class BaseRecyclerFragment<P extends IPresenter> extends BaseFragment<P> {

    /**
     * 拖动类型：下拉刷新
     */
    public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;

    /**
     * 拖动类型：上拉加载
     */
    public static final int MODE_PULL_UP_TO_LOAD_MORE = 0x2;

    /**
     * 拖动类型：下拉刷新 && 上拉加载
     */
    public static final int MODE_BOTH = 0x3;

    /**
     * 拖动类型：无拖动
     */
    public static final int MODE_NONE = 0x4;

    /**
     * 默认布局
     */
    public static final int NO_LAYOUT = 0;

    protected RecyclerView mRv;
    protected SmartRefreshLayout mSRL;
    protected BaseSimpleRVAdapter mAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.base_recycler_fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
        mRv = ((RecyclerView) contentView.findViewById(R.id.rv));
        mSRL = ((SmartRefreshLayout) contentView.findViewById(R.id.refresh_layout));

        initRecyclerView();

        initRefresh();
        initHeaderFooter();

        init(savedInstanceState);
    }

    private void initRecyclerView() {
        mRv.setLayoutManager(createLayoutManager());
        mAdapter = createAdapter();
        mRv.setAdapter(mAdapter);
    }

    protected abstract BaseSimpleRVAdapter createAdapter();

    private void initHeaderFooter() {
        if (getHeaderLayout() != NO_LAYOUT) {
            View header = LayoutInflater.from(getContext()).inflate(getHeaderLayout(), mRv, false);
            onInitHeader(header);
            mAdapter.setHeaderView(header);

        }

        if (getFooterLayout() != NO_LAYOUT) {
            View footer = LayoutInflater.from(getContext()).inflate(getFooterLayout(), mRv, false);
            onInitFooter(footer);
            mAdapter.setFooterView(footer);
        }
    }

    private void initRefresh() {
        mSRL.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BaseRecyclerFragment.this.onRefresh();
            }
        });

        mSRL.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                BaseRecyclerFragment.this.onLoadMore();
            }
        });
    }

    /**
     * 设置额外的上面布局
     *
     * @return ExtraLayoutId
     */
    protected int getHeaderLayout() {
        return NO_LAYOUT;
    }

    /**
     * 设置额外的底部布局
     *
     * @return ExtraLayoutId
     */
    protected int getFooterLayout() {
        return NO_LAYOUT;
    }

    /**
     * Bottom初始化操作
     *
     * @param view bottom
     */

    protected void onInitHeader(View view) {
    }

    /**
     * Bottom初始化操作
     *
     * @param view bottom
     */

    protected void onInitFooter(View view) {
    }

    /**
     * 加载更多
     */
    protected void onLoadMore() {

    }

    /**
     * 刷新
     */
    protected void onRefresh() {

    }

    /**
     * 设置刷行模式, default: MODE_BOTH
     *
     * @param mode MODE_BOTH， MODE_NONE，MODE_PULL_DOWN_TO_REFRESH，MODE_PULL_UP_TO_LOAD_MORE
     */
    protected void setMode(int mode) {
        switch (mode) {
            case MODE_BOTH:
                mSRL.setEnableRefresh(true);
                mSRL.setEnableLoadmore(true);
                break;
            case MODE_NONE:
                mSRL.setEnableRefresh(false);
                mSRL.setEnableLoadmore(false);
                break;
            case MODE_PULL_DOWN_TO_REFRESH:
                mSRL.setEnableRefresh(true);
                mSRL.setEnableLoadmore(false);
                break;
            case MODE_PULL_UP_TO_LOAD_MORE:
                mSRL.setEnableRefresh(false);
                mSRL.setEnableLoadmore(true);
                break;
        }
    }

    /**
     * 结束页面滑动
     */
    public void finishRefreshAndLoadMore() {
        if (mSRL != null) {
            mSRL.finishRefresh();
            mSRL.finishLoadmore();
        }
    }

    /**
     * 初始化操作
     *
     * @param savedInstanceState Bundle
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 设置布局管理器
     */
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

}
