package com.uplan.miyao.ui.main.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import com.uplan.miyao.R;
import com.uplan.miyao.base.UiUtils;
import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.ui.main.contract.MainContract;
import com.uplan.miyao.ui.main.model.MainModel;
import com.uplan.miyao.ui.main.model.resp.BannerInfoResp;
import com.uplan.miyao.ui.main.model.resp.VersionResp;
import com.uplan.miyao.util.RxUtils;
import com.uplan.miyao.util.SystemUtils;
import com.uplan.miyao.widget.UpdateDownloadDialog;
import com.uplan.miyao.widget.UpdateVersionDialog;
import com.yitoudai.update.UpdateManager;

import timber.log.Timber;


public class MainPresenter extends BasePresenter<MainContract.View, MainContract.Model> {

    private static  Activity context;

    public MainPresenter(MainContract.View view,Activity context) {
        super(view);
        this.context=context;
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
                checkNeedUpdate(versionResp);
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
    private static void checkNeedUpdate(VersionResp versionCode){
        boolean newVersionCode = compareVersion(UiUtils.getContext(), versionCode.version);
        if(newVersionCode){
            showUpdateTipDialog(versionCode);
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
     */
    private static void showUpdateTipDialog( VersionResp versionResp) {
        UpdateVersionDialog updateVersionDialog = new UpdateVersionDialog(context).builder();
        updateVersionDialog.setVersionName(String.format(context.getString(R.string.update_version_name_tip)))
                .setVersionContent(versionResp.msg)
                .setCloseClickListener(true)
                .setImmediateUpdate(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        download(context, versionResp);
                    }
                }).show();
    }

    /**
     * 开始下载
     *
     * @param context     上下文
     */
    private static void download(Activity context, VersionResp versionResp) {
        //进度对话框
        UpdateDownloadDialog downloadDialog = new UpdateDownloadDialog(context).builder();
        downloadDialog.setVersionCurrent(SystemUtils.getVersionName(context)).setVersionUpdate(versionResp.version+"");

        UpdateManager.getInstance().createBuilder().
                setContext(context).
                setVersionCode(versionResp.version).
                setDownloadUrl("http://www.51mix.cn/appClient/getApp").
                setDownloadFileName("miyao").
                setAppName("蜜钥").
                setAppLogoResource(R.drawable.miyao).
                setDownloadListener(
                        new com.yitoudai.update.DownloadListener() {
                            @Override
                            public void onDownloadStart() {
                                downloadDialog.show();
                            }

                            @Override
                            public void onDownloadFail(String message) {
                                downloadDialog.setProgress(0);

                                    SystemClock.sleep(500);
                                    UpdateManager.getInstance().startDownload();


                            }

                            @Override
                            public void onProgressChange(int progress) {
                                downloadDialog.setProgress(progress);
                            }

                            @Override
                            public void onDownloadCancel() {
                                downloadDialog.dismiss();
                            }

                            @Override
                            public void onDownloadFinish() {
                                downloadDialog.dismiss();
                            }
                        }).build();
    }


}
