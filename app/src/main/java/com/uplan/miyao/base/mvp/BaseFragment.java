package com.uplan.miyao.base.mvp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.AppBaseFragment;
import com.uplan.miyao.base.CommonPage;
import com.uplan.miyao.base.UiUtils;

/**
 * update by zs on 2017/8/29.
 * updateDesc:统一管理Loading  Empty  Error Success
 */
public abstract class BaseFragment<P extends IPresenter> extends AppBaseFragment {

    /** 页面加载状态 */
    public static final int PAGE_STATUS_LOADING = CommonPage.PAGE_STATUS_LOADING;//加载状态
    public static final int PAGE_STATUS_EMPTY = CommonPage.PAGE_STATUS_EMPTY;//空数据状态
    public static final int PAGE_STATUS_ERROR = CommonPage.PAGE_STATUS_ERROR;//失败状态
    public static final int PAGE_STATUS_SUCCESS = CommonPage.PAGE_STATUS_SUCCESS;//成功状态

    /** 默认布局 */
    public static final int NO_LAYOUT = 0;

    /** mPresenter的具体实例由子类初始化 */
    protected P mPresenter;

    /** 统一管理页面的CommonPage */
    private CommonPage mCommonPage;

    /** 内容布局 */
    private View mContentLayout;

    private Dialog mLoadingDialog;

    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base, container, false);
        LinearLayout homeView = (LinearLayout) rootView.findViewById(R.id.ll_root);
        mCommonPage = new CommonPage(UiUtils.getContext()) {
            @Override
            protected int getLoadingView() {
                return getLoadingLayout();
            }

            @Override
            protected int getEmptyView() {
                return getEmptyLayout();
            }

            @Override
            protected void onEmptyViewInit(View emptyView) {
                onEmptyLayoutInit(emptyView);
            }

            @Override
            protected int getErrorView() {
                return getErrorLayout();
            }

            @Override
            protected void onErrorViewInit(View errorView) {
                onErrorLayoutInit(errorView);
            }

            @Override
            protected int getSuccessView() {
                return getContentLayout();
            }

            @Override
            protected void onSuccessViewInit(View successView) {
                mContentLayout = successView;
            }
        };

        if(getTitleLayout() != NO_LAYOUT){
            View titleView = UiUtils.inflateView(getTitleLayout(), homeView);
            if(titleView != null){
                onTitleLayoutInit(titleView);
                homeView.addView(titleView);
            }
        }

        homeView.addView(mCommonPage, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = getPresenter();

        init(savedInstanceState, mContentLayout);
    }

    /**
     * 标题栏布局：子类如果需要特殊标题样式或不需要标题时需重写getTitleLayout和onTitleLayoutInit方法
     *
     * @return TitleLayoutId
     */
    protected int getTitleLayout(){
        return 0;
    }

    /**
     * 标题栏布局初始化：子类如果需要特殊标题样式需重写getTitleLayout和onTitleLayoutInit方法
     *
     * @param titleView 标题栏
     */
    protected void onTitleLayoutInit(View titleView) {

    }

    /**
     * 正在加载布局
     *
     * @return LoadingLayoutId
     */
    protected int getLoadingLayout(){
        return R.layout.common_loading;
    }

    /**
     * 空页面布局
     *
     * @return EmptyLayoutId
     */
    protected int getEmptyLayout(){
        return R.layout.common_empty;
    }

    /**
     * 错误页面布局
     *
     * @return ErrorLayoutId
     */
    protected int getErrorLayout(){
        return R.layout.common_error;
    }

    /**
     * 具体页面的内容布局
     *
     * @return ContentLayoutId
     */
    protected abstract int getContentLayout();

    /**
     * 初始化操作
     *
     * @param savedInstanceState Bundle
     */
    protected abstract void init(Bundle savedInstanceState, View contentView);

    /**
     * 获取presenter
     *
     * @return Presenter
     */
    protected abstract P getPresenter();

    /**
     * 加载空页面初始化
     *
     * @param emptyView 空布局
     */
    protected void onEmptyLayoutInit(View emptyView){

    }

    /**
     * 加载失败布局初始化
     *
     * @param errorView 失败布局
     */
    protected void onErrorLayoutInit(View errorView){
        TextView reload = (TextView) errorView.findViewById(R.id.btn_reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReload();
            }
        });
    }

    /**
     * 加载失败重新加载
     */
    protected void onReload(){
        setPageStatus(PAGE_STATUS_LOADING);
    }

    /**
     * 设置页面状态
     *
     * @param pageStatus 页面状态
     */
    public void setPageStatus(int pageStatus){
        if(mCommonPage != null){
            mCommonPage.setPageStatus(pageStatus);
        }
    }

    /**
     * 获取页面状态
     */
    public int getPageStatus(){
        if(mCommonPage != null){
            return mCommonPage.getPageStatus();
        }

        return PAGE_STATUS_ERROR;
    }

    /**
     * 显示Loading对话框
     */
    protected void showLoadingDialog(){
        if(mLoadingDialog == null){
            mLoadingDialog = new Dialog(getActivity(), R.style.photo_dialog_style);
        }
        mLoadingDialog.setContentView(R.layout.dialog_load);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }

    /**
     * 隐藏Loading对话框
     */
    protected void hideLoadingDialog(){
        if(mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

}
