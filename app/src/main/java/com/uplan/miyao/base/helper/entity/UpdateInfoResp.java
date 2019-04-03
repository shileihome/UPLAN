package com.uplan.miyao.base.helper.entity;

import java.util.List;

/**
 * Author: Created by zs on 2018/7/25.
 *
 * Description:
 */

public class UpdateInfoResp {
    public String version;
    public int versionCode;
    public String updateContent;
    public String downloadUrl;
    public List<HistoryUpdateType> updateTypes;

    @Override
    public String toString() {
        return "UpdateInfoResp{" + "version='" + version + '\'' + ", versionCode=" + versionCode +
                ", updateContent='" + updateContent + '\'' + ", downloadUrl='" + downloadUrl +
                '\'' + ", updateTypes=" + updateTypes + '}';
    }
}
