package com.uplan.miyao.base.mvp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.uplan.miyao.R;
import com.uplan.miyao.base.AppBaseFragment;
import com.uplan.miyao.base.CommonPage;

/**
 * update by zs on 2017/8/29.
 * updateDesc:统一管理Loading  Empty  Error Success
 */
public abstract class BaseFragment<P extends IPresenter> extends AppBaseFragment {


    /** mPresenter的具体实例由子类初始化 */
    protected P mPresenter;

    /** 统一管理页面的CommonPage */
    private CommonPage mCommonPage;

    private Dialog mLoadingDialog;

    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }


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
