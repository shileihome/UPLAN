/*
package com.uplan.miyao.app.util;

import android.app.Dialog;
import android.content.Context;

import com.uplan.miyao.app.R;
import IView;
import Api;
import ErrorHandleSubscriber;
import com.uplan.miyao.app.ui.order.model.entity.LoadLinkResp;
import NetWorkConfig;
import CommonModel;

import timber.log.Timber;

*/
/**
 * Author: Created by shilei on 2018/6/12-13:11
 * Description: 工单复制下载链接
 *//*

public class LoadLinkUtils  {

    private Dialog mLoadingDialog;
    private static LoadLinkUtils instance;

    private LoadLinkUtils(){
    }


    public static LoadLinkUtils getInstance(){
        if(instance==null){
            synchronized(LoadLinkUtils.class){
                if(instance==null){
                    instance=new LoadLinkUtils();
                }
            }
        }
        return instance;
    }

    */
/**
     * 复制下载链接
     *
     * @param id 工单id
     *//*

    public void loadLink(String id,IView iView,Context context){
        showLoadingDialog(context);
        CommonModel.loadLink(id).compose(RxUtils.applySchedulers(iView)).subscribe(new ErrorHandleSubscriber<LoadLinkResp>() {
                @Override
                public void onSuccess(LoadLinkResp loadLinkResp) {
                    hideLoadingDialog();
                    String link= Api.BASE_URL+ NetWorkConfig.LINK_APPEND+id+" 密码:"+loadLinkResp.data.password;
                    ClipboardUtils.setClipboardUtils(context,"合同链接",link);
                    Timber.d("contract_link: " + link);
                    ToastUtils.showCopyLink(context);
                }

                @Override
                public void onFailure(int code, String msg) {
                    hideLoadingDialog();
                    ToastUtils.shortShow(msg);
                }
            });
    }

    */
/**
     * 显示Loading对话框
     *//*

    protected void showLoadingDialog(Context context){
        mLoadingDialog = new Dialog(context, R.style.photo_dialog_style);
        mLoadingDialog.setContentView(R.layout.dialog_load);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.show();
    }

    */
/**
     * 隐藏Loading对话框
     *//*

    protected void hideLoadingDialog(){
        if(mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }
}
*/
