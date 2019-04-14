package com.uplan.miyao.ui.splash;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.app.ActivityManager;
import com.uplan.miyao.base.AppBaseActivity;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.ui.main.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by zs on 2018/4/13.
 *
 * Description: 页面中转分发页
 */

public class SplashActivity extends AppBaseActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.tv_splash_time)
    TextView tvSplashTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        MyCountDownTimer mCountDownTimer= new MyCountDownTimer(3000,1000);
        mCountDownTimer.start();
        setTranslucent();
    }

    /**
     * 根据状态判断进入具体页面
     */
    private void startToMain() {
        MainActivity.start(this);

        ActivityManager.getScreenManager().popActivity(this);
    }

    @OnClick(R.id.tv_splash_time)
    public void onClick() {
        startToMain();
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture 表示以「 毫秒 」为单位倒计时的总数
         * 例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         * 例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        public void onFinish() {
            tvSplashTime.setText("0s 跳过");
            startToMain();
        }

        public void onTick(long millisUntilFinished) {
            tvSplashTime.setText(millisUntilFinished / 1000 + "s 跳过");
        }

    }

    public void setTranslucent(){
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
    }
}
