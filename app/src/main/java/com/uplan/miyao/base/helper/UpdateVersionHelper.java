/*
package com.uplan.miyao.base.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.uplan.miyao.R;
import com.uplan.miyao.net.RequestInterceptor;
import com.uplan.miyao.base.AppBaseActivity;
import com.uplan.miyao.base.UiUtils;
import com.uplan.miyao.base.helper.entity.HistoryUpdateType;
import com.uplan.miyao.base.helper.entity.UpdateInfoResp;
import com.uplan.miyao.net.constant.ErrorConstant;
import com.uplan.miyao.net.constant.NetWorkConfig;
import com.uplan.miyao.util.ApkUtils;
import com.uplan.miyao.util.ToastUtils;
import com.uplan.miyao.widget.UpdateVersionDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

*/
/**
 * Author: Created by zs on 2018/4/26.
 *
 * Description: 版本更新帮助类
 *//*


public class UpdateVersionHelper {

    */
/**  版本更新: 无最新版本*//*

    private static final int UPDATE_NO_NEW_VERSION = 0;

    */
/**  版本更新: 不提示更新*//*

    private static final int UPDATE_NO_PROMPT = 1;

    */
/**  版本更新: 提示更新*//*

    private static final int UPDATE_HAVE_PROMPT = 2;

    */
/**  版本更新: 强制更新*//*

    private static final int UPDATE_FORCE = 3;

    */
/** 下载失败重试次数 *//*

    private static final int RETRY_DOWNLOAD_COUNT = 5;

    */
/** 当前已下载次数 *//*

    private static int mCurrentDownloadCount;

    private UpdateVersionHelper() {}

    */
/**
     * 检查版本更新
     *
     * @param context   上下文
     * @param isSetting true:来自设置页  false:首页
     *//*

    public static void checkUpdateVersion(Activity context, boolean isSetting) {
        String url = NetWorkConfig.UPDATE_VERSION;
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new RequestInterceptor()).build();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Timber.d("version_update : failure");
                dealFailure(ErrorConstant.ERROR_NETWORK);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws
                    IOException {
                Timber.d("version_update : response- " + response.toString());
                ResponseBody body = response.body();

                //网络异常
                if (response.code() != 200 || body == null) {
                    dealFailure(ErrorConstant.ERROR_NETWORK);
                    return;
                }

                String json = body.string();
                Timber.d("version_info : " + json);
                try {
                    UpdateInfoResp updateInfoResp = new Gson().fromJson(json, UpdateInfoResp.class);
                    if(checkData(updateInfoResp)){
                        dealSuccess(context, updateInfoResp, isSetting);
                    }else {
                        dealFailure("数据解析异常");
                    }
                }catch (Exception ex){
                    //ignore
                }
            }
        });
    }

    */
/**
     * 失败处理
     *
     * @param message 错误信息
     *//*

    private static void dealFailure(String message) {
        UiUtils.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                Timber.d("version_update: " + message);
            }
        });
    }

    */
/**
     * 成功处理
     *
     * @param context     上下文
     * @param isSetting   true:来自设置页  false:首页
     *//*

    private static void dealSuccess(Activity context, UpdateInfoResp updateInfoResp, boolean isSetting) {
        UiUtils.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                boolean isNeedUpdate = checkNeedUpdate(updateInfoResp, isSetting);

                if (!isNeedUpdate && isSetting) {
                    ToastUtils.shortShow("已是最新版本！");
                    return;
                }

                if(!isNeedUpdate ){
                    return;
                }

                if (context == null || context.isFinishing()) {
                    return;
                }

                if (context instanceof AppBaseActivity) {
                    boolean isForeground = ((AppBaseActivity) context).isForeground();
                    if (!isForeground) {
                        return;
                    }

                    showUpdateTipDialog(context, updateInfoResp);
                }

            }
        });
    }

    */
/**
     * 弹出提示信息
     *
     * @param context 上下文
     *//*

    private static void showUpdateTipDialog(Activity context, UpdateInfoResp updateInfoResp) {
        UpdateVersionDialog updateVersionDialog = new UpdateVersionDialog(context).builder();
        updateVersionDialog.setVersionName(String.format(context.getString(R.string.update_version_name_tip), updateInfoResp.version))
                .setVersionContent(updateInfoResp.updateContent)
                .setCloseClickListener(getUpdateTypeByVersionCode(updateInfoResp) == UPDATE_FORCE)
                .setImmediateUpdate(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        download(context, updateInfoResp);
                    }
                }).show();
    }

    */
/**
     * 开始下载
     *
     * @param context     上下文
     *//*

    private static void download(Activity context, UpdateInfoResp updateInfoResp) {
 */
/*       //进度对话框
        UpdateDownloadDialog downloadDialog = new UpdateDownloadDialog(context).builder();
        downloadDialog.setVersionCurrent(SystemUtils.getVersionName(context)).setVersionUpdate(updateInfoResp.version);

        Timber.d("version_name, downloadVersionCode -> " + ApkUtils.convertVersionCode(context, updateInfoResp.version));
        UpdateManager.getInstance().createBuilder().
                setContext(context).
                setVersionCode(updateInfoResp.versionCode).
                setDownloadUrl(updateInfoResp.downloadUrl).
                setDownloadFileName("ZRX").
                setAppName("中融信").
                setAppLogoResource(R.mipmap.ic_launcher).
                setDownloadListener(
                        new DownloadListener() {
                            @Override
                            public void onDownloadStart() {
                                downloadDialog.show();
                            }

                            @Override
                            public void onDownloadFail(String message) {
                                downloadDialog.setProgress(0);
                                if(mCurrentDownloadCount < RETRY_DOWNLOAD_COUNT){
                                    mCurrentDownloadCount++;
                                    SystemClock.sleep(500);
                                    UpdateManager.getInstance().startDownload();
                                    Log.d("version_update: 正在重试 ", mCurrentDownloadCount +"");
                                }else {
                                    downloadDialog.dismiss();
                                    mCurrentDownloadCount = 0;

                                    ToastUtils.shortShow(message);
                                    Timber.d("version_update: 下载失败");
                                }
                            }

                            @Override
                            public void onProgressChange(int progress) {
                                downloadDialog.setProgress(progress);
                            }

                            @Override
                            public void onDownloadCancel() {
                                downloadDialog.dismiss();
                                mCurrentDownloadCount = 0;
                            }

                            @Override
                            public void onDownloadFinish() {
                                downloadDialog.dismiss();
                                mCurrentDownloadCount = 0;
                            }
                        }).build();*//*

    }

    */
/**
     * 检查是否需要更新
     *
     * @param updateInfoResp 更新信息
     * @param isSetting 点击来源是否来自设置页
     * @return true: 需要更新  false: 不需要更新
     *//*

    private static boolean checkNeedUpdate(UpdateInfoResp updateInfoResp, boolean isSetting){
        boolean newVersionCode = ApkUtils.checkNeedUpdate(UiUtils.getContext(), updateInfoResp.versionCode);
        if(!newVersionCode){
            return false;
        }

        int updateType = getUpdateTypeByVersionCode(updateInfoResp);

        //设置页 && updateType >= 1
        if(isSetting && updateType >= UPDATE_NO_PROMPT){
            return true;
        }

        //首页
        return updateType >= UPDATE_HAVE_PROMPT;
    }

    */
/**
     * 根据当前版本获取更新策略
     *
     * @param updateInfoResp 更新信息
     * @return 更新策略
     *//*

    private static int getUpdateTypeByVersionCode(UpdateInfoResp updateInfoResp){
        int updateType = UPDATE_NO_NEW_VERSION;
        int localVersionCode = SystemUtils.getVersionCode(UiUtils.getContext());

        for (HistoryUpdateType historyUpdateType: updateInfoResp.updateTypes) {
            if(historyUpdateType.versionCode == localVersionCode){
                updateType = historyUpdateType.updateType;
                break;
            }
        }
        return updateType;
    }

    */
/**
     * 检测数据
     *
     * @param updateInfoResp 更新信息
     * @return true: 数据合法， false: 数据异常
     *//*

    private static boolean checkData(UpdateInfoResp updateInfoResp){
        if(updateInfoResp == null){
            return false;
        }

        Log.e("version_update_info: ", updateInfoResp.toString());
        if(TextUtils.isEmpty(updateInfoResp.version)){
            return false;
        }

        if(TextUtils.isEmpty(updateInfoResp.downloadUrl)){
            return false;
        }

        if(updateInfoResp.versionCode == 0){
            return false;
        }

        if(updateInfoResp.updateTypes == null || updateInfoResp.updateTypes.isEmpty()){
            return false;
        }

        return true;
    }
}
*/
