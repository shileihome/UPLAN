package com.uplan.miyao.ui.financial.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.uplan.miyao.ui.financial.view.activity.FinancialWebActivity_1;
import com.uplan.miyao.ui.financial.view.activity.FinancialWebActivity_2;
import com.uplan.miyao.ui.financial.view.activity.FinancialWebActivity_3;
import com.uplan.miyao.ui.financial.view.activity.FinancialWebActivity_4;
import com.uplan.miyao.ui.financial.view.activity.LucencyActivity;
import com.uplan.miyao.ui.financial.view.activity.SafeActivity;
import com.uplan.miyao.ui.financial.view.activity.SimpleActivity;
import com.uplan.miyao.ui.financial.view.activity.TeamInfoActivity;
import com.uplan.miyao.ui.financial.view.activity.ValidActivity;
import com.uplan.miyao.ui.financial.view.activity.YinMiDetailActivity;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.regist.view.activity.PlatformActivity;
import com.uplan.miyao.ui.regist.view.activity.PrivacyActivity;
import com.uplan.miyao.ui.vip.view.activity.VipActivity;
import com.uplan.miyao.util.EncodeUtils;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.youngkaaa.yviewpager.YViewPager;


public class HomeFragment extends BaseFragment<FinancialPresenter> implements FinancialContract.View {


    @BindView(R.id.viewpager_y)
    YViewPager viewpagerVertical;

    TextView tvLogin;
    TextView tvHomeBuy1;
    TextView tvHomeBuy2;
    TextView tvHomeBuy3;

    TextView tvNotifyNum2;
    TextView tvNotifyNum1;


    boolean loginState;
    //本地图片BannerTop
    private ArrayList<Bitmap> localImagesTop = new ArrayList<Bitmap>();

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
        setTranslucent();
        initViews();
        initVerticalViewPager();
        if (!PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.HOME_SHOW_DIALOG, false)) {
            showDialog();
        }

        return view;
    }

    /**
     * 这是上下滑动
     */
    private void initVerticalViewPager() {
        YViewPagerAdapter adapter = new YViewPagerAdapter(verticalViews, getActivity());
        viewpagerVertical.setAdapter(adapter);
        viewpagerVertical.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private void initViews() {
        View firstLayout = View.inflate(getActivity(), R.layout.view_home_vertical_viewpager_1, null);
        View secondLayout = View.inflate(getActivity(), R.layout.view_home_vertical_viewpager_2, null);
        verticalViews.add(firstLayout);
        verticalViews.add(secondLayout);

        LMBanners bannersTop = (LMBanners) firstLayout.findViewById(R.id.banners_top);
        addBannerTops();
        initBannerTop(bannersTop);
        ViewPager viewpagerCenter = (ViewPager) firstLayout.findViewById(R.id.viewpager_center);
        initCenterViews();
        initCenterViewPager(viewpagerCenter);

        //第一个页面点击事件--------------------------------------------------------------
        tvLogin = (TextView) firstLayout.findViewById(R.id.tv_login);
        tvLogin.setOnClickListener(view -> {
            LoginActivity.start(getActivity());
        });
        RelativeLayout rlNotify1 = (RelativeLayout) firstLayout.findViewById(R.id.rl_notify_1);
        rlNotify1.setOnClickListener(view -> {
            if (isShowLoginDialog()) {
                return;
            }
            RemindActivity.start(getActivity());
        });
        tvNotifyNum1 = (TextView) firstLayout.findViewById(R.id.tv_notify_num_1);


        //第二个页面点击事件--------------------------------------------------------------
        RelativeLayout cvSafe = (RelativeLayout) secondLayout.findViewById(R.id.cv_safe);
        cvSafe.setOnClickListener(view -> {
            SafeActivity.start(getActivity());
        });
        RelativeLayout cvLucency = (RelativeLayout) secondLayout.findViewById(R.id.cv_lucency);
        cvLucency.setOnClickListener(view -> {
            LucencyActivity.start(getActivity());
        });
        RelativeLayout cvValid = (RelativeLayout) secondLayout.findViewById(R.id.cv_valid);
        cvValid.setOnClickListener(view -> {
            ValidActivity.start(getActivity());
        });
        RelativeLayout cvSimple = (RelativeLayout) secondLayout.findViewById(R.id.cv_simple);
        cvSimple.setOnClickListener(view -> {
            SimpleActivity.start(getActivity());
        });
        ImageView ivTeamInfo = (ImageView) secondLayout.findViewById(R.id.iv_team_info);
        ivTeamInfo.setOnClickListener(view -> {
            TeamInfoActivity.start(getActivity());
        });
        RelativeLayout rlNotify2 = (RelativeLayout) secondLayout.findViewById(R.id.rl_notify_2);
        rlNotify2.setOnClickListener(view -> {
            if (isShowLoginDialog()) {
                return;
            }
            RemindActivity.start(getActivity());
        });
        TextView tvYinMiDetail = (TextView) secondLayout.findViewById(R.id.tv_yinmi_detail);
        tvYinMiDetail.setOnClickListener(view -> {
            YinMiDetailActivity.start(getActivity());
        });
        tvNotifyNum2 = (TextView) secondLayout.findViewById(R.id.tv_notify_num_2);

        TextView tvHomePlatform = (TextView) secondLayout.findViewById(R.id.tv_platform);
        TextView tvHomePrivacy = (TextView) secondLayout.findViewById(R.id.tv_privacy);

        tvHomePlatform.setOnClickListener(v -> {
            PlatformActivity.start(getActivity());
        });
        tvHomePrivacy.setOnClickListener(v -> {
            PrivacyActivity.start(getActivity());
        });

    }

    /**
     * 设置中部滑动
     */
    private void initCenterViewPager(ViewPager viewpagerCenter) {
        CenterViewPageAdapter adapter = new CenterViewPageAdapter(views, getActivity());
        viewpagerCenter.setAdapter(adapter);
    }

    private void initCenterViews() {
        View view_1 = View.inflate(getActivity(), R.layout.view_home_center_1, null);
        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_CHANPIN1))) {
            Bitmap bitmap = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_CHANPIN1));
            ((ImageView) view_1.findViewById(R.id.iv_center_1)).setImageBitmap(bitmap);
        }

        View view_2 = View.inflate(getActivity(), R.layout.view_home_center_2, null);
        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_CHANPIN2))) {
            Bitmap bitmap = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_CHANPIN2));
            ((ImageView) view_2.findViewById(R.id.iv_center_2)).setImageBitmap(bitmap);
        }
        View view_3 = View.inflate(getActivity(), R.layout.view_home_center_3, null);
        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_CHANPIN3))) {
            Bitmap bitmap = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_CHANPIN3));
            ((ImageView) view_3.findViewById(R.id.iv_center_3)).setImageBitmap(bitmap);
        }

        View view_4 = View.inflate(getActivity(), R.layout.view_home_center_4, null);
        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_CHANPIN4))) {
            Bitmap bitmap = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_CHANPIN4));
            ((ImageView) view_4.findViewById(R.id.iv_center_4)).setImageBitmap(bitmap);
        }

        views.add(view_1);
        views.add(view_2);
        views.add(view_3);
        views.add(view_4);

        view_1.setOnClickListener(view1 -> {
            if (isShowLoginDialog()) {
                return;
            }
            if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BOOLEAN_HOME_CH1_ISVIP)) &&
                    PreferencesUtils.getString(getActivity(), PreferencesUtils.BOOLEAN_HOME_CH1_ISVIP).equals(PreferencesUtils.CH_ISVIP_1)
            ) {
                if (!PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(getActivity());
                            }).show();
                    return;
                } else {
                    if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL1_HOME))) {
                        FinancialWebActivity_1.start(getActivity(), PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL1_HOME));
                    }
                }
            } else {
                if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL1_HOME))) {
                    FinancialWebActivity_1.start(getActivity(), PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL1_HOME));
                }
            }

        });
        tvHomeBuy1 = (TextView) view_1.findViewById(R.id.tv_home_buy_1);

        view_2.setOnClickListener(view2 -> {
            if (isShowLoginDialog()) {
                return;
            }
            if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BOOLEAN_HOME_CH2_ISVIP)) &&
                    PreferencesUtils.getString(getActivity(), PreferencesUtils.BOOLEAN_HOME_CH2_ISVIP).equals(PreferencesUtils.CH_ISVIP_1)
            ) {
                if (!PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(getActivity());
                            }).show();
                    return;
                } else {
                    if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL2_HOME))) {
                        FinancialWebActivity_2.start(getActivity(), PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL2_HOME));
                    }
                }
            } else {
                if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL2_HOME))) {
                    FinancialWebActivity_2.start(getActivity(), PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL2_HOME));
                }
            }
        });
        tvHomeBuy2 = (TextView) view_2.findViewById(R.id.tv_home_buy_2);

        view_3.setOnClickListener(view3 -> {
            if (isShowLoginDialog()) {
                return;
            }
            if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BOOLEAN_HOME_CH3_ISVIP)) &&
                    PreferencesUtils.getString(getActivity(), PreferencesUtils.BOOLEAN_HOME_CH3_ISVIP).equals(PreferencesUtils.CH_ISVIP_1)
            ) {
                if (!PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(getActivity());
                            }).show();
                    return;
                } else {
                    if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL3_HOME))) {
                        FinancialWebActivity_3.start(getActivity(), PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL3_HOME));
                    }
                }
            } else {
                if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL3_HOME))) {
                    FinancialWebActivity_3.start(getActivity(), PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL3_HOME));
                }
            }
        });
        tvHomeBuy3 = (TextView) view_3.findViewById(R.id.tv_home_buy_3);
        view_4.setOnClickListener(view4 -> {
            if (isShowLoginDialog()) {
                return;
            }
            if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BOOLEAN_HOME_CH4_ISVIP)) &&
                    PreferencesUtils.getString(getActivity(), PreferencesUtils.BOOLEAN_HOME_CH4_ISVIP).equals(PreferencesUtils.CH_ISVIP_1)
            ) {
                if (!PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(getActivity());
                            }).show();
                    return;
                } else {
                    if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL4_HOME))) {
                        FinancialWebActivity_4.start(getActivity(), PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL4_HOME));
                    }
                }
            } else {
                if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL4_HOME))) {
                    FinancialWebActivity_4.start(getActivity(), PreferencesUtils.getString(getActivity(), PreferencesUtils.URL_CHURL4_HOME));
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loginState = PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.LOGIN_STATE);
        if (loginState) {
            tvLogin.setVisibility(View.GONE);
            tvHomeBuy1.setVisibility(View.VISIBLE);
            tvHomeBuy2.setVisibility(View.VISIBLE);
            tvHomeBuy3.setVisibility(View.VISIBLE);
        } else {
            tvLogin.setVisibility(View.VISIBLE);
            tvHomeBuy1.setVisibility(View.INVISIBLE);
            tvHomeBuy2.setVisibility(View.INVISIBLE);
            tvHomeBuy3.setVisibility(View.INVISIBLE);
        }
        String num = PreferencesUtils.getString(getActivity(), PreferencesUtils.MESSAGE_NUM);
        if (Integer.parseInt(num.trim()) > 0) {
            tvNotifyNum1.setVisibility(View.VISIBLE);
            tvNotifyNum2.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected FinancialPresenter getPresenter() {
        return new FinancialPresenter(this);
    }


    @Override
    public void dealFailure(int code, String message) {

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
        bannersTop.setScrollDurtion(1500);//两页切换时间
        bannersTop.setCanLoop(true);//循环播放
        bannersTop.setSelectIndicatorRes(R.drawable.select_indicator);//选中的原点
        bannersTop.setUnSelectUnIndicatorRes(R.drawable.unselect_indicator);//未选中的原点
        //若自定义原点到底部的距离,默认20,必须在setIndicatorWidth之前调用
        bannersTop.setIndicatorBottomPadding(5);
        bannersTop.setIndicatorWidth(5);//原点默认为5dp
        bannersTop.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        bannersTop.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
        bannersTop.setDurtion(2000);//轮播切换时间
//        bannersTop.hideIndicatorLayout();//隐藏原点
//        bannersTop.showIndicatorLayout();//显示原点
        bannersTop.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置


        //本地用法
        bannersTop.setAdapter(new LocalImgAdapter(getActivity(), LocalImgAdapter.TYPE_HOME), localImagesTop);
        //网络图片
//        bannersTop.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);

    }

    private void addBannerTops() {
        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_HOME_1))) {
            Bitmap bitmap = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_HOME_1));
            localImagesTop.add(bitmap);
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.focus_financial_1);
            localImagesTop.add(bmp);
        }
        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_HOME_2))) {
            Bitmap bitmap = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_HOME_2));
            localImagesTop.add(bitmap);
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.focus_financial_2);
            localImagesTop.add(bmp);
        }

        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_HOME_3))) {
            Bitmap bitmap = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_HOME_3));
            localImagesTop.add(bitmap);
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.focus_financial_3);
            localImagesTop.add(bmp);
        }
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


    boolean isPrivacy = false;

    public void showDialog() {

        CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
        LinearLayout llContent = commonDialog.getLlContent();
        TextView tvOnly = commonDialog.getTvOnly();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_dialog_content, null);
        ImageView rbPrivacy = (ImageView) view.findViewById(R.id.rb_privacy);
        TextView tvPlatform = (TextView) view.findViewById(R.id.tv_platform);
        TextView tvPrivacy = (TextView) view.findViewById(R.id.tv_privacy);
        rbPrivacy.setImageResource(R.drawable.privacy_unselect);
        tvOnly.setEnabled(false);
        rbPrivacy.setOnClickListener(v -> {
            if (isPrivacy) {
                isPrivacy = false;
                tvOnly.setEnabled(false);
                rbPrivacy.setImageResource(R.drawable.privacy_unselect);
            } else {
                isPrivacy = true;
                tvOnly.setEnabled(true);
                rbPrivacy.setImageResource(R.drawable.privacy_select);
            }
        });
        tvPlatform.setOnClickListener(v -> {
            PlatformActivity.start(getActivity());
        });
        tvPrivacy.setOnClickListener(v -> {
            PrivacyActivity.start(getActivity());
        });
        llContent.addView(view);
        commonDialog.setCancelable(false);
        commonDialog.
                setOnlyButton("确定", v -> {
                    PreferencesUtils.putBoolean(getActivity(), PreferencesUtils.HOME_SHOW_DIALOG, true);
                    commonDialog.dismiss();
                }).show();
    }
}
