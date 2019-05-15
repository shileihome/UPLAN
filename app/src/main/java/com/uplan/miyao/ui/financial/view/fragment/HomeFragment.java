package com.uplan.miyao.ui.financial.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.transformer.TransitionEffect;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.uplan.miyao.R;
import com.uplan.miyao.app.UPLANApplication;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.account.view.activity.RemindActivity;
import com.uplan.miyao.ui.financial.adapter.CenterViewPageAdapter;
import com.uplan.miyao.ui.financial.adapter.LocalImgAdapter;
import com.uplan.miyao.ui.financial.adapter.YViewPagerAdapter;
import com.uplan.miyao.ui.financial.contract.FinancialContract;
import com.uplan.miyao.ui.financial.presenter.FinancialPresenter;
import com.uplan.miyao.ui.financial.view.activity.FinancialActivity;
import com.uplan.miyao.ui.financial.view.activity.LucencyActivity;
import com.uplan.miyao.ui.financial.view.activity.SafeActivity;
import com.uplan.miyao.ui.financial.view.activity.SimpleActivity;
import com.uplan.miyao.ui.financial.view.activity.TeamInfoActivity;
import com.uplan.miyao.ui.financial.view.activity.ValidActivity;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.ToastUtils;
import com.uplan.miyao.widget.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.youngkaaa.yviewpager.YViewPager;


public class HomeFragment extends BaseFragment<FinancialPresenter> implements FinancialContract.View {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_team_info)
    ImageView tvTeamInfo;

    boolean loginState;
    @BindView(R.id.banners_top)
    LMBanners bannersTop;

    @BindView(R.id.tv_home_buy)
    TextView tvHomeBuy;
    @BindView(R.id.viewpager_center)
    ViewPager viewpagerCenter;
    @BindView(R.id.viewpager_y)
    YViewPager viewpagerVertical;
    @BindView(R.id.ll_first_layout)
    LinearLayout llFirstLayout;
    //本地图片BannerTop
    private ArrayList<Integer> localImagesTop = new ArrayList<Integer>();

    //中间投资计划
    private List<View> views = new ArrayList<View>();

    //上下滑动View集合
    private List<View> verticalViews = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        initImageLoader();
//        addBannerTops();
//        initBannerTop();
        setTranslucent();
        initVerticalViews();
        initVerticalViewPager();
//        initPagerViews();
//        initViewPager();
        return view;
    }

    /**
     * 这是上下滑动
     */
    private void initVerticalViewPager() {
        YViewPagerAdapter adapter =new YViewPagerAdapter(verticalViews,getActivity());
        viewpagerVertical.setAdapter(adapter);
        viewpagerVertical.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private void initVerticalViews(){
        View firstLayout = View.inflate(getActivity(), R.layout.view_home_vertical_viewpager_1, null);
        View secondLayout=View.inflate(getActivity(),R.layout.view_home_vertical_viewpager_2,null);
        verticalViews.add(firstLayout);
        verticalViews.add(secondLayout);

        LMBanners bannersTop= (LMBanners) firstLayout.findViewById(R.id.banners_top);
        addBannerTops();
        initBannerTop(bannersTop);
        ViewPager viewpagerCenter = (ViewPager) firstLayout.findViewById(R.id.viewpager_center);
        initPagerViews();
        initViewPager(viewpagerCenter);
    }

    /**
     * 设置中部滑动
     */
    private void initViewPager(ViewPager viewpagerCenter) {
        CenterViewPageAdapter adapter = new CenterViewPageAdapter(views, getActivity());
        viewpagerCenter.setAdapter(adapter);
        viewpagerCenter.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPagerViews() {
        View view_1 = View.inflate(getActivity(), R.layout.view_home_center_1, null);
        View view_2 = View.inflate(getActivity(), R.layout.view_home_center_2, null);
        View view_3 = View.inflate(getActivity(), R.layout.view_home_center_3, null);
        views.add(view_1);
        views.add(view_2);
        views.add(view_3);

        view_1.setOnClickListener(view1 -> {
            ToastUtils.shortShow("心塞塞");
        });
        view_2.setOnClickListener(view2 -> {
            ToastUtils.shortShow("是啊");
        });
        view_3.setOnClickListener(view3 -> {
            ToastUtils.shortShow("我也是");
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loginState = PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.LOGIN_STATE);
        if (loginState) {
            tvLogin.setVisibility(View.GONE);
            tvHomeBuy.setVisibility(View.VISIBLE);
        } else {
            tvLogin.setVisibility(View.VISIBLE);
            tvHomeBuy.setVisibility(View.GONE);
        }
    }


    @Override
    protected FinancialPresenter getPresenter() {
        return new FinancialPresenter(this);
    }


    @Override
    public void dealFailure(int code, String message) {

    }


    @OnClick({R.id.iv_center, R.id.iv_notify_2, R.id.iv_notify_1, R.id.tv_login, R.id.cv_safe, R.id.tv_team_info, R.id.tv_home_buy, R.id.cv_lucency, R.id.cv_simple, R.id.cv_valid})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_center:
                if (isShowLoginDialog()) {
                    return;
                }
                FinancialActivity.start(getActivity());
                break;
            case R.id.tv_login:
                LoginActivity.start(getActivity());
                break;

            case R.id.cv_safe:
                SafeActivity.start(getActivity());
                break;
            case R.id.cv_lucency:
                LucencyActivity.start(getActivity());
                break;
            case R.id.cv_simple:
                SimpleActivity.start(getActivity());
                break;
            case R.id.cv_valid:
                ValidActivity.start(getActivity());
                break;
            case R.id.tv_team_info:
                TeamInfoActivity.start(getActivity());
                break;
            case R.id.tv_home_buy:
                FinancialActivity.start(getActivity());
                break;
            case R.id.iv_notify_1:
                if (isShowLoginDialog()) {
                    return;
                }
                RemindActivity.start(getActivity());
                break;
            case R.id.iv_notify_2:
                if (isShowLoginDialog()) {
                    return;
                }
                RemindActivity.start(getActivity());
                break;

        }
    }

    private void initBannerTop(LMBanners bannersTop) {
        //设置Banners高度
//      bannersTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(getActivity(), 210)));
        bannersTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        //参数设置
        bannersTop.isGuide(false);//是否为引导页
        bannersTop.setAutoPlay(true);//自动播放
//        bannersTop.setAutoPlay(false);//自动播放
        bannersTop.setVertical(false);//是否锤子播放
        bannersTop.setScrollDurtion(2000);//两页切换时间
        bannersTop.setCanLoop(true);//循环播放
        bannersTop.setSelectIndicatorRes(R.drawable.select_indicator);//选中的原点
        bannersTop.setUnSelectUnIndicatorRes(R.drawable.unselect_indicator);//未选中的原点
        //若自定义原点到底部的距离,默认20,必须在setIndicatorWidth之前调用
        bannersTop.setIndicatorBottomPadding(5);
        bannersTop.setIndicatorWidth(5);//原点默认为5dp
        bannersTop.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        bannersTop.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
        //  bannersTop.setDurtion(3000);//轮播切换时间
//        bannersTop.hideIndicatorLayout();//隐藏原点
//        bannersTop.showIndicatorLayout();//显示原点
        bannersTop.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置


        //本地用法
        bannersTop.setAdapter(new LocalImgAdapter(getActivity()), localImagesTop);
        //网络图片
//        bannersTop.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);

    }

    private void addBannerTops() {
        localImagesTop.add(R.drawable.focus_financial_1);
        localImagesTop.add(R.drawable.focus_financial_2);
        localImagesTop.add(R.drawable.focus_financial_3);
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

    protected boolean isShowLoginDialog() {
        if (!isLogined()) {
            CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
            commonDialog.setSubMessage("请先登录!").
                    setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                    }).
                    setRightButton(getString(R.string.commit_change), v -> {
                        LoginActivity.start(getActivity());
                    }).show();
            return true;
        } else {
            return false;
        }
    }

    private boolean isLogined() {
        return PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.LOGIN_STATE);
    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
        QMUIStatusBarHelper.translucent(getActivity());
        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
    }

}
