package com.uplan.miyao.util;

/**
 * Author: Created by shilei on 2019/7/12-12:03
 * Description:
 */

import android.content.Context;
import android.webkit.DownloadListener;

public class DownloadBuilder {
    private String mDownloadUrl;
    private String mDownloadFileName;
    private String mAppName;
    private int mAppLogoResource;
    private int mVersionCode = 0;
    private DownloadListener mDownloadListener;
    private Context mContext;

    public DownloadBuilder() {
    }

    public DownloadBuilder setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
        return this;
    }

    public DownloadBuilder setDownloadFileName(String downloadFileName) {
        this.mDownloadFileName = downloadFileName;
        return this;
    }

    public DownloadBuilder setContext(Context context) {
        this.mContext = context;
        return this;
    }

    public DownloadBuilder setAppName(String appName) {
        this.mAppName = appName;
        return this;
    }

    public DownloadBuilder setAppLogoResource(int appLogoResource) {
        this.mAppLogoResource = appLogoResource;
        return this;
    }

    public DownloadBuilder setVersionCode(int versionCode) {
        this.mVersionCode = versionCode;
        return this;
    }

    public DownloadBuilder setDownloadListener(DownloadListener downloadListener) {
        this.mDownloadListener = downloadListener;
        return this;
    }

    public String getDownloadUrl() {
        return this.mDownloadUrl;
    }

    public String getDownloadFileName() {
        return this.mDownloadFileName;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public int getAppLogoResource() {
        return this.mAppLogoResource;
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public DownloadListener getDownloadListener() {
        return this.mDownloadListener;
    }

    public DownloadBuilder build() {
        UpdateManager.getInstance().startDownload();
        return this;
    }
}

