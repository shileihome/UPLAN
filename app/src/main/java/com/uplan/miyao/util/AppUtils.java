package com.uplan.miyao.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * <pre>
 *     author : cfp
 *     e-mail : chengfangpeng@foxmail.com
 *     time   : 2017/08/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class AppUtils {


    /**
     *  判断app是否在前台
     * @param context
     * @return
     */
    public static boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {

            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
