package com.uplan.miyao.util;

import timber.log.Timber;

/**
 * Author: Created by fangmingdong on 2018/4/23-下午7:41
 * Description:
 */
public class ConvertUtils {

    public static long str2Int(String str) {
        long ret = 0;
        try {
            ret = Long.parseLong(str);
        } catch (NumberFormatException e) {
            Timber.e(e);
        }
        return ret;
    }
}
