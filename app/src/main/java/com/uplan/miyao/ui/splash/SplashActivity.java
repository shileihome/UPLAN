package com.uplan.miyao.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.uplan.miyao.R;
import com.uplan.miyao.app.ActivityManager;
import com.uplan.miyao.base.AppBaseActivity;
import com.uplan.miyao.ui.home.view.activity.MainActivity;

/**
 * Author: Created by zs on 2018/4/13.
 *
 * Description: 页面中转分发页
 */

public class SplashActivity extends AppBaseActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                start();
            }
        }, 2000);
    }

    /**
     * 根据状态判断进入具体页面
     */
    private void start() {
            MainActivity.start(this);

        ActivityManager.getScreenManager().popActivity(this);
    }
}
