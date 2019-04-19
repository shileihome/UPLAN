package com.uplan.miyao.ui.financial.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.transformer.TransitionEffect;
import com.allure.lbanners.utils.ScreenUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.uplan.miyao.R;
import com.uplan.miyao.app.UPLANApplication;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.financial.adapter.LocalImgAdapter;
import com.uplan.miyao.ui.financial.contract.FinancialContract;
import com.uplan.miyao.ui.financial.presenter.FinancialPresenter;
import com.uplan.miyao.ui.financial.view.activity.SafeActivity;
import com.uplan.miyao.ui.financial.view.activity.TeamInfoActivity;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.util.PreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment<FinancialPresenter> implements FinancialContract.View {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.cv_safe)
    CardView tvSafe;
    @BindView(R.id.tv_team_info)
    TextView tvTeamInfo;

    boolean loginState;
    @BindView(R.id.banners_top)
    LMBanners bannersTop;
    @BindView(R.id.banners_center)
    LMBanners bannersCenter;

    //本地图片BannerTop
    private ArrayList<Integer> localImagesTop = new ArrayList<Integer>();

    //本地图片BannerCenter
    private ArrayList<Integer> localImagesCenter = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);

        initImageLoader();

        addBannerCenters();
        addBannerTops();

        initBannerCenter();
        initBannerTop();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loginState = PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.LOGIN_STATE);
        if (loginState) {
            tvLogin.setVisibility(View.GONE);
        } else {
            tvLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected FinancialPresenter getPresenter() {
        return new FinancialPresenter(this);
    }


    @Override
    public void dealFailure(int code, String message) {

    }


    @OnClick({R.id.tv_login, R.id.tv_financial, R.id.cv_safe, R.id.tv_team_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                LoginActivity.start(getActivity());
                break;
            case R.id.tv_financial:
/*                if (loginState) {
                    FinancialActivity.start(getActivity());
                } else {
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先登陆!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {

                            }).
                            setRightButton(getString(R.string.commit_change), v -> {

                                LoginActivity.start(getActivity());
                            }).show();
                }*/
                break;
            case R.id.cv_safe:
                SafeActivity.start(getActivity());
                break;
            case R.id.tv_team_info:
                TeamInfoActivity.start(getActivity());
                break;
        }
    }

  private void initBannerTop(){
      //设置Banners高度
      bannersTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(getActivity(), 170)));


      //参数设置
      bannersTop.isGuide(false);//是否为引导页
      bannersTop.setAutoPlay(true);//自动播放
      bannersTop.setVertical(false);//是否锤子播放
      bannersTop.setScrollDurtion(2000);//两页切换时间
      bannersTop.setCanLoop(true);//循环播放
      bannersTop.setSelectIndicatorRes(R.drawable.select_indicator);//选中的原点
      bannersTop.setUnSelectUnIndicatorRes(R.drawable.unselect_indicator);//未选中的原点
      //若自定义原点到底部的距离,默认20,必须在setIndicatorWidth之前调用
      bannersTop.setIndicatorBottomPadding(0);
      bannersTop.setIndicatorWidth(5);//原点默认为5dp
      bannersTop.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        bannersTop.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
      bannersTop.setDurtion(3000);//轮播切换时间
//        bannersTop.hideIndicatorLayout();//隐藏原点
//        bannersTop.showIndicatorLayout();//显示原点
      bannersTop.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置


      //本地用法
      bannersTop.setAdapter(new LocalImgAdapter(getActivity()), localImagesTop);
      //网络图片
//        bannersTop.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);

      bannersTop.setOnStartListener(0XFFAABBCC, 0XFFAACCBB, new LMBanners.onStartListener() {
          @Override
          public void startOpen() {
              //回调跳转的逻辑
              Toast.makeText(getActivity(), "我要进入主界面", Toast.LENGTH_LONG).show();

          }
      });
  }
  private void initBannerCenter(){
      //设置Banners高度
      bannersCenter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(getActivity(), 135)));


      //参数设置
      bannersCenter.isGuide(false);//是否为引导页
      bannersCenter.setAutoPlay(false);//自动播放
      bannersCenter.setVertical(false);//是否锤子播放
      bannersCenter.setCanLoop(true);//循环播放
      bannersCenter.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
        bannersCenter.hideIndicatorLayout();//隐藏原点

      //本地用法
      bannersCenter.setAdapter(new LocalImgAdapter(getActivity()), localImagesCenter);
      //网络图片
//        bannersCenter.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);

      bannersCenter.setOnStartListener(0XFFAABBCC, 0XFFAACCBB, new LMBanners.onStartListener() {
          @Override
          public void startOpen() {
              //回调跳转的逻辑
              Toast.makeText(getActivity(), "我要进入主界面", Toast.LENGTH_LONG).show();

          }
      });
  }

  private void addBannerTops(){
        localImagesCenter.add(R.drawable.center_financial);
        localImagesCenter.add(R.drawable.center_financial2);
  }
  private void addBannerCenters(){
        localImagesTop.add(R.drawable.focus_financial);
        localImagesTop.add(R.drawable.focus_financial2);
  }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                UPLANApplication.getContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

}
