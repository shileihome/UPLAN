package com.uplan.miyao.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.support.multidex.MultiDex;

import com.uplan.miyao.BuildConfig;
import com.uplan.miyao.base.helper.SmartRefreshInitHelper;

import timber.log.Timber;

/**
 * Created by zs on 2018/4/3.
 *
 * 应用Application
 */

public class UPLANApplication extends Application {

    private static UPLANApplication mInstance;
    private static Handler mHandler;
    private static int mMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mHandler = new Handler();
        mMainThreadId = Process.myTid();

        initTimber();
        initSmartRefreshLayout();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 获取Handler
     *
     * @return Handler
     */
    public static Handler getHandler(){
        return mHandler;
    }

    /**
     * 获取主线程Id
     *
     * @return 主线程Id
     */
    public static int getMainThreadId(){
        return mMainThreadId;
    }

    /**
     * 获取Application实例
     *
     * @return UPLANApplication
     */
    public static UPLANApplication getInstance(){
        return mInstance;
    }

    /**
     * 获取Context
     *
     * @return context
     */
    public static Context getContext(){
        return getInstance().getApplicationContext();
    }

     /**
     * 初始化下拉刷新组件
     */
    private void initSmartRefreshLayout() {
        SmartRefreshInitHelper.initSmartRefresh();
    }

    /**
     * 初始化Timber
     */
    private void initTimber() {
        if (BuildConfig.LOG_DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
