package com.uplan.miyao.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.transformer.TransitionEffect;
import com.uplan.miyao.R;
import com.uplan.miyao.app.ActivityManager;
import com.uplan.miyao.base.AppBaseActivity;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.ui.financial.adapter.LocalImgAdapter;
import com.uplan.miyao.ui.main.view.activity.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: Created by zs on 2018/4/13.
 *
 * Description: 页面中转分发页
 */

public class SplashActivity extends AppBaseActivity {


    @BindView(R.id.banners_splash)
    LMBanners bannersSplash;
    //本地图片BannerTop
    private ArrayList<Integer> localImagesTop = new ArrayList<Integer>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        /*MyCountDownTimer mCountDownTimer= new MyCountDownTimer(3000,1000);
        mCountDownTimer.start();*/
        setTranslucent();
        addBannerSplash();
        initBannerSplash();

    }

    /**
     * 根据状态判断进入具体页面
     */

    private void startToMain() {
        MainActivity.start(this);

        ActivityManager.getScreenManager().popActivity(this);
    }

/*    @OnClick(R.id.tv_splash_time)
    public void onClick() {
        startToMain();
    }

    class MyCountDownTimer extends CountDownTimer {
        */

    /**
     *  millisInFuture 表示以「 毫秒 」为单位倒计时的总数
     * 例如 millisInFuture = 1000 表示1秒
     *  countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
     * 例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
     *//*


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
*/

    private void initBannerSplash() {
      //设置Banners高度
        bannersSplash.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        //参数设置
        bannersSplash.isGuide(true);//是否为引导页
            bannersSplash.setAutoPlay(true);//自动播放
        bannersSplash.setVertical(false);//是否锤子播放
        bannersSplash.setScrollDurtion(1000);//两页切换时间
        bannersSplash.setCanLoop(true);//循环播放
        bannersSplash.setSelectIndicatorRes(R.drawable.splash_select);//选中的原点
        bannersSplash.setUnSelectUnIndicatorRes(R.drawable.splash_unselect);//未选中的原点
        //若自定义原点到底部的距离,默认20,必须在setIndicatorWidth之前调用
//        bannersSplash.setIndicatorBottomPadding(5);
        bannersSplash.setIndicatorWidth(6);//原点默认为5dp
        bannersSplash.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
  //        bannersSplash.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
          bannersSplash.setDurtion(3000);//轮播切换时间
//        bannersSplash.hideIndicatorLayout();//隐藏原点
        bannersSplash.showIndicatorLayout();//显示原点
        bannersSplash.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置

        //本地用法
        bannersSplash.setAdapter(new LocalImgAdapter(this), localImagesTop);
        //网络图片
//        bannersSplash.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);

        bannersSplash.setOnStartListener(R.drawable.shape_btn_splash, 0X00ffffff, new LMBanners.onStartListener() {
            @Override
            public void startOpen() {
                //回调跳转的逻辑
                startToMain();
            }
        });

    }
    private void addBannerSplash(){
        localImagesTop.add(R.drawable.splash_banner_1);
        localImagesTop.add(R.drawable.splash_banner_2);
        localImagesTop.add(R.drawable.splash_banner_3);
    }
    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
}
