package com.uplan.miyao.util;

/**
 * Author: Created by shilei on 2019/7/12-12:09
 * Description:
 */
public interface DownloadListener {
    void onDownloadStart();

    void onDownloadFail(String var1);

    void onProgressChange(int var1);

    void onDownloadCancel();

    void onDownloadFinish();
}
