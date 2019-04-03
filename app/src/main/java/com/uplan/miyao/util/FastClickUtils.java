package com.uplan.miyao.util;

/**
 * Created by zs on 2017/11/29.
 *
 * 快速点击工具类
 */

public class FastClickUtils {

    private static long sLastClickTime;

    /**
     * 判断是否是快速点击，判断标准：小于0.5秒
     * ss
     *
     * @return boolean
     */
    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - sLastClickTime < 500) {
            return true;
        }

        sLastClickTime = time;
        return false;
    }

    /**
     * 判断是否是快速点击，判断标准：< limitTime
     *
     * @param limitTime 限定时间，单位(s)
     * @return boolean
     */
    public static boolean isFastClick(long limitTime) {
        long time = System.currentTimeMillis();
        if (time - sLastClickTime < limitTime) {
            return true;
        }

        sLastClickTime = time;
        return false;
    }
}
