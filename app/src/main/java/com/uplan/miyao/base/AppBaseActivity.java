package com.uplan.miyao.base;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.uplan.miyao.app.ActivityManager;

import timber.log.Timber;

/**
 * AppBaseActivity
 */

@SuppressLint("Registered")
public class AppBaseActivity extends RxAppCompatActivity {

    /** 当前页面是否在前台显示 */
    private boolean mIsForeground = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.e("(Activity Name) ==> " + getClass().getName());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityManager.getScreenManager().pushActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsForeground = true;
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsForeground = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getScreenManager().popActivity(this);
    }

    /**
     * 判断是否在前台
     *
     * @return true: 在前台  false : 不在前台
     */
    public boolean isForeground() {
        return mIsForeground;
    }
}
