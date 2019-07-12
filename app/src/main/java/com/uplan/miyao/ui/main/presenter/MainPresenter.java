package com.uplan.miyao.ui.main.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.uplan.miyao.R;
import com.uplan.miyao.base.UiUtils;
import com.uplan.miyao.base.helper.entity.UpdateInfoResp;
import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.ui.main.contract.MainContract;
import com.uplan.miyao.ui.main.model.MainModel;
import com.uplan.miyao.ui.main.model.resp.BannerInfoResp;
import com.uplan.miyao.ui.main.model.resp.VersionResp;
import com.uplan.miyao.util.ApkUtils;
import com.uplan.miyao.util.DownloadListener;
import com.uplan.miyao.util.RxUtils;
import com.uplan.miyao.util.SystemUtils;
import com.uplan.miyao.util.ToastUtils;
import com.uplan.miyao.util.UpdateManager;
import com.uplan.miyao.widget.UpdateDownloadDialog;
import com.uplan.miyao.widget.UpdateVersionDialog;

import timber.log.Timber;


public class MainPresenter extends BasePresenter<MainContract.View, MainContract.Model> {

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    protected MainContract.Model getModel() {
        return new MainModel();
    }


    public void getBannerInfo() {
        // mView.loading();
        mModel.getBannerInfo().compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<BannerInfoResp>(){

            @Override
            public void onSuccess(BannerInfoResp bannerInfoResp) {
                mView.dealBannerSuccess(bannerInfoResp);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.dealFailure(code, msg);
            }
        });
    }

    public void getVersionCode(){
        mModel.getVersionCode().compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<VersionResp>(){

            @Override
            public void onSuccess(VersionResp versionResp) {

            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    /**
     * 检查是否需要更新
     *
     * @return true: 需要更新  false: 不需要更新
     */
    private static void checkNeedUpdate(String versionCode){
        boolean newVersionCode = compareVersion(UiUtils.getContext(), Integer.parseInt(versionCode));
        if(!newVersionCode){

        }
    }

    /**
     * 检测是否需要更新
     *
     * @param context     上下文
     * @param versionCode 版本号
     * @return true: 需要更新  false: 不需要更新
     */
    public static boolean compareVersion(Context context, int versionCode) {
        int localVersionCode = SystemUtils.getVersionCode(context);

        Timber.d("version_code: localVersionCode" + localVersionCode);
        Timber.d("version_code: remoteVersionCode" + versionCode);
        return versionCode > localVersionCode;
    }

    /**
     * 弹出提示信息
     *
     * @param context 上下文
     */
    private static void showUpdateTipDialog(Activity context, VersionResp versionResp) {
        UpdateVersionDialog updateVersionDialog = new UpdateVersionDialog(context).builder();
        updateVersionDialog.setVersionName(String.format(context.getString(R.string.update_version_name_tip), updateInfoResp.version))
                .setVersionContent(versionResp.msg)
                .setCloseClickListener(false)
                .setImmediateUpdate(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        download(context, updateInfoResp);
                    }
                }).show();
    }



}
