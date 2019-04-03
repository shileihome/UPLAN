package com.uplan.miyao.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 统一管理页面Loading  Empty  Error Success的View组件
 */

public abstract class CommonPage extends FrameLayout {

    /** 页面状态：正在加载 */
    public static final int PAGE_STATUS_LOADING = 0XFF01;

    /** 页面状态：空页面 */
    public static final int PAGE_STATUS_EMPTY = 0XFF02;

    /** 页面状态：加载成功 */
    public static final int PAGE_STATUS_SUCCESS = 0XFF03;

    /** 页面状态：加载失败 */
    public static final int PAGE_STATUS_ERROR = 0XFF04;

    /** 当前页面状态 */
    private int mCurrentPageStatus = PAGE_STATUS_LOADING;

    /** 正在加载View */
    private View mLoadingView;

    /** 加载失败View */
    private View mErrorView;

    /** 空页面View */
    private View mEmptyView;

    /** 加载失败View */
    private View mSuccessView;

    public CommonPage(@NonNull Context context) {
        super(context);

        init();
    }

    /**
     * 初始化
     */
    private void init(){
        //正在加载布局
        mLoadingView = UiUtils.inflateView(getLoadingView());
        if(mLoadingView != null){
            onLoadingViewInit(mLoadingView);
            addView(mLoadingView);
        }

        //加载失败布局
        mErrorView = UiUtils.inflateView(getErrorView());
        if(mErrorView != null){
            onErrorViewInit(mErrorView);
            addView(mErrorView);
        }

        //空页面布局
        mEmptyView = UiUtils.inflateView(getEmptyView());
        if(mEmptyView != null){
            onEmptyViewInit(mEmptyView);
            addView(mEmptyView);
        }

        //成功页面布局
        mSuccessView = UiUtils.inflateView(getSuccessView());
        if(mSuccessView != null){
            onSuccessViewInit(mSuccessView);
            addView(mSuccessView);
        }

        //显示View
        showRightPage();
    }

    /**
     * 加载View初始化
     *
     * @param loadingView 加载View
     */
    protected void onLoadingViewInit(View loadingView) {}

    /**
     * 失败View初始化
     *
     * @param errorView 失败View
     */
    protected void onErrorViewInit(View errorView) {}

    /**
     * 空数据View初始化
     *
     * @param emptyView 空数据View
     */
    protected void onEmptyViewInit(View emptyView) {}

    /**
     * 成功View初始化
     *
     * @param successView 成功View
     */
    protected void onSuccessViewInit(View successView) {}

    /**
     * 创建正在加载View
     *
     * @return LoadingView
     */
    protected abstract int getLoadingView();

    /**
     * 创建空页面View
     *
     * @return EmptyView
     */
    protected abstract int getEmptyView();

    /**
     * 创建错误页面View
     *
     * @return ErrorView
     */
    protected abstract int getErrorView();

    /**
     * 创建加载成功View
     *
     * @return SuccessView
     */
    protected abstract int getSuccessView();

    /**
     * 设置当前页面状态
     *
     * @param pageStatus 页面状态
     */
    public void setPageStatus(int pageStatus){
        this.mCurrentPageStatus = pageStatus;

        showRightPage();
    }

    /**
     * 获取当前页面状态
     *
     * @return 页面状态
     */
    public int getPageStatus(){
        return mCurrentPageStatus;
    }

    /**
     * 根据当前页面状态，正确显示View
     */
    private void showRightPage() {

        if(mLoadingView != null){
            mLoadingView.setVisibility(mCurrentPageStatus == PAGE_STATUS_LOADING ? View.VISIBLE : View.GONE);
        }

        if(mErrorView != null){
            mErrorView.setVisibility(mCurrentPageStatus == PAGE_STATUS_ERROR ? View.VISIBLE : View.GONE);
        }

        if(mEmptyView != null){
            mEmptyView.setVisibility(mCurrentPageStatus == PAGE_STATUS_EMPTY ? View.VISIBLE : View.GONE);
        }

        if(mSuccessView != null){
            mSuccessView.setVisibility(mCurrentPageStatus == PAGE_STATUS_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }
}
