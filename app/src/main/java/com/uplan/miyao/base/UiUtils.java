package com.uplan.miyao.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uplan.miyao.app.UPLANApplication;

/**
 * UI常用工具类
 */
public class UiUtils {

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext(){
        return UPLANApplication.getContext();
    }

    /**
     * 加载布局
     *
     * @param resId layoutId
     * @return View
     */
    public static View inflateView(int resId){
        return View.inflate(getContext(), resId, null);
    }

    /**
     * 加载布局
     *
     * @param resId layoutId
     * @return View
     */
    public static View inflateView(int resId, ViewGroup viewGroup){
        return LayoutInflater.from(getContext()).inflate(resId, viewGroup, false);
    }

    /**
     * 获取Handler对象
     *
     * @return Handler
     */
    public static Handler getMainThreadHandler(){
        return UPLANApplication.getHandler();
    }

    /**
     * 获取主线程Id
     *
     * @return 主线程Id
     */
    public static int getMainThreadId(){
        return UPLANApplication.getMainThreadId();
    }

    /**
     * 获取字符串
     *
     * @param resId resourceId
     * @return String
     */
    public static String getString(int resId){
        return getContext().getResources().getString(resId);
    }

    /**
     * 获取颜色
     *
     * @param resId resourceId
     * @return Color
     */
    public static int getColor(int resId){
        return getContext().getResources().getColor(resId);
    }

    /**
     * 获取图片资源
     *
     * @param resId resourceId
     * @return Drawable
     */
    public static Drawable getDrawable(int resId){
        return getContext().getResources().getDrawable(resId);
    }

    /**
     * 获取dimen下的值
     *
     * @param resId resourceId
     * @return int
     */
    public static int getDimen(int resId){
        return getContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * 判断当前线程是否是主线程
     *
     * @return boolean
     */
    public static boolean isRunOnUiThread(){
        //获取主线程的Id
        int mainThreadId = getMainThreadId();
        //获取当前方法被调用的线程Id
        int currentThreadId = Process.myTid();
        //判断两个线程Id
        return mainThreadId == currentThreadId;
    }

    /**
     * 保证当前runnable一定执行在主线程
     *
     * @param runnable runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isRunOnUiThread()) {
            runnable.run();
        } else {
            getMainThreadHandler().post(runnable);
        }
    }
}
