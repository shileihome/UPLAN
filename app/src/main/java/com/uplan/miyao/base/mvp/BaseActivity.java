package com.uplan.miyao.base.mvp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.AppBaseActivity;
import com.uplan.miyao.base.CommonPage;
import com.uplan.miyao.base.UiUtils;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * update by zs on 2017/8/29.
 * updateDesc:统一管理Loading  Empty  Error Success
 */
public abstract class BaseActivity<P extends IPresenter> extends AppBaseActivity {

    /** 页面加载状态 */
    public static final int PAGE_STATUS_LOADING = CommonPage.PAGE_STATUS_LOADING;//加载状态
    public static final int PAGE_STATUS_EMPTY = CommonPage.PAGE_STATUS_EMPTY;//空数据状态
    public static final int PAGE_STATUS_ERROR = CommonPage.PAGE_STATUS_ERROR;//失败状态
    public static final int PAGE_STATUS_SUCCESS = CommonPage.PAGE_STATUS_SUCCESS;//成功状态

    /** 默认布局 */
    public static final int NO_LAYOUT = 0;

    /** mPresenter的具体实例由子类初始化 */
    protected P mPresenter;

    /** ButterKnife绑定 */
    private Unbinder mUnBind;

    /** 统一管理页面的CommonPage */
    private CommonPage mCommonPage;

    /** Title标题栏控件 */
    protected ImageView mLeftIcon;
    protected TextView mTitle;
    protected ImageView mRightIcon;
    protected TextView mTvRightText;
    protected View mPartLine;

    private View mContentView;
    private Dialog mLoadingDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = UiUtils.inflateView(R.layout.activity_base);
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
            protected int getSuccessView() {
                return getContentLayout();
            }

            @Override
            protected void onErrorViewInit(View errorView) {
                onErrorLayoutInit(errorView);
            }

            @Override
            protected void onSuccessViewInit(View successView) {
                mContentView = successView;
            }
        };

        //加载标题栏
        if(getTitleLayout() != NO_LAYOUT){
            View titleView = UiUtils.inflateView(getTitleLayout(), homeView);
            if(titleView != null){
                onTitleLayoutInit(titleView);
                homeView.addView(titleView);
            }
        }

        //加载内容布局
        homeView.addView(mCommonPage,LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(rootView);

        mUnBind = ButterKnife.bind(this, mContentView);
        setStatusBar();
        mPresenter = getPresenter();

        init(savedInstanceState, mContentView);
    }


    /**
     * 标题栏布局：子类如果需要特殊标题样式或不需要标题时需重写getTitleLayout和onTitleLayoutInit方法
     *
     * @return TitleLayoutId
     */
    protected int getTitleLayout(){
        return R.layout.common_title;
    }

    /**
     * 标题栏布局初始化：子类如果需要特殊标题样式需重写getTitleLayout和onTitleLayoutInit方法
     *
     * @param titleView 标题栏
     */
    protected void onTitleLayoutInit(View titleView) {
        mLeftIcon = (ImageView) titleView.findViewById(R.id.iv_left_icon);

        mTitle = (TextView) titleView.findViewById(R.id.tv_title);
        mRightIcon = (ImageView) titleView.findViewById(R.id.iv_right_icon);
        mTvRightText = (TextView) titleView.findViewById(R.id.tv_right_text);
        mPartLine = (View)titleView.findViewById(R.id.part_line);

        mTitle.setText(getTitleText());
        mLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    /**
     * 标题
     *
     * @return 标题内容
     */
    protected String getTitleText(){
        return "";
    }

    /**
     * 返回
     */
    protected void goBack(){
        finish();
    }

    /**
     * 设置左边按钮图片
     *
     * @param resId 图片Id
     * @param listener 监听
     */
    protected void setLeftImage(int resId, final View.OnClickListener listener){
        mLeftIcon.setImageResource(resId);
        mLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }

    /**
     * 设置右边按钮图片
     *
     * @param resId 图片Id
     * @param listener 监听
     */
    protected void setRightImage(int resId, final View.OnClickListener listener){
        mRightIcon.setVisibility(View.VISIBLE);
        mTvRightText.setVisibility(View.GONE);
        mRightIcon.setImageResource(resId);
        mRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }

    /**
     * 设置右边文字
     *
     * @param text 文案
     * @param listener 监听
     */
    protected void setRightText(CharSequence text, final View.OnClickListener listener){
        mRightIcon.setVisibility(View.GONE);
        mTvRightText.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTvRightText.setText(text);
        mTvRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }

    /**
     * 设置状态栏
     */
    protected void setStatusBar(){
        QMUIStatusBarHelper.setStatusBarLightMode(this);
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
     * 页面的初始化工作
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
     * 设置沉浸式状态栏
     */
    public void setTranslucent(){
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
    }

    /**
     * 显示Loading对话框
     */
    protected void showLoadingDialog(){
        if(mLoadingDialog == null){
            mLoadingDialog = new Dialog(this, R.style.photo_dialog_style);
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

    /**
     * 当前工单状态是否是查看状态
     *
     * @return true: 查看状态  false：
     */
    public boolean isLook(){
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mUnBind != null && mUnBind != Unbinder.EMPTY)
            mUnBind.unbind();
        this.mUnBind = null;
        if (mPresenter != null)
            mPresenter.onDestroy();
        this.mPresenter = null;
    }
}
