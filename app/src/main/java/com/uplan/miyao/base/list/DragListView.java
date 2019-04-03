package com.uplan.miyao.base.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uplan.miyao.R;

/**
 * 可拖拉的ListView
 */

public class DragListView extends FrameLayout{

    /** 拖动类型：下拉刷新 */
    public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;

    /** 拖动类型：上拉加载 */
    public static final int MODE_PULL_UP_TO_LOAD_MORE = 0x2;

    /** 拖动类型：下拉刷新 && 上拉加载 */
    public static final int MODE_BOTH = 0x3;

    /** 拖动类型：无拖动 */
    public static final int MODE_NONE = 0x4;

    /** listView */
    private ListView mListView;

    /** 拖拉控件 */
    private SmartRefreshLayout mRefreshLayout;

    /** 拖拉监听 */
    private OnDragListViewListener mOnDragListViewListener;

    public DragListView(Context context) {
        super(context);
        initView();
    }

    public DragListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.base_list_activity, this);
        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);
        mListView = (ListView) view.findViewById(R.id.list_view);

        initDragListener();
    }

    /**
     * 初始化拖拉控件监听
     */
    private void initDragListener() {
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(mOnDragListViewListener != null){
                    mOnDragListViewListener.onRefresh(refreshlayout);
                }
            }
        });

        //上拉加载更多
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(mOnDragListViewListener != null){
                    mOnDragListViewListener.onLoadMore(refreshlayout);
                }
            }
        });
    }

    /**
     * 获取ListView
     *
     * @return ListView
     */
    public ListView getListView(){
        if(mListView == null){
            throw new NullPointerException("DragListView : this ListView is null");
        }

        return mListView;
    }

    /**
     * 设置拖拉监听
     *
     * @param onDragListViewListener 拖拉监听
     */
    public void setOnRefreshListener(OnDragListViewListener onDragListViewListener){
        this.mOnDragListViewListener = onDragListViewListener;
    }

    /**
     * 刷新拖动类型
     */
    public void setRefreshMode(int mode) {
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
     * 结束拖拉动作
     */
    public void pullDone(){
        if(mRefreshLayout != null){
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadmore();
        }
    }

    /**
     * 拖拉监听
     */
    public interface OnDragListViewListener{

        /**
         * 下拉刷新
         *
         * @param refreshLayout 拖拉控件
         */
        void onRefresh(RefreshLayout refreshLayout);

        /**
         * 上拉加载更多
         *
         * @param refreshLayout 拖拉控件
         */
        void onLoadMore(RefreshLayout refreshLayout);
    }
}
