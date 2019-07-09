package com.uplan.miyao.ui.vip.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.transformer.TransitionEffect;
import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIDisplayHelper;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.financial.adapter.LocalImgAdapter;
import com.uplan.miyao.ui.financial.view.activity.YinMiDetailActivity;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.survey.view.SurveyActivity;
import com.uplan.miyao.ui.vip.contract.DiscoverContract;
import com.uplan.miyao.ui.vip.model.resp.VipDetailResp;
import com.uplan.miyao.ui.vip.presenter.DiscoverPresenter;
import com.uplan.miyao.ui.vip.view.activity.ClassroomWebActivity;
import com.uplan.miyao.ui.vip.view.activity.FundGroupWebActivity;
import com.uplan.miyao.ui.vip.view.activity.InsuranceWebActivity;
import com.uplan.miyao.ui.vip.view.activity.MerchantWebActivity;
import com.uplan.miyao.ui.vip.view.activity.SharedActivity;
import com.uplan.miyao.ui.vip.view.activity.VipActivity;
import com.uplan.miyao.util.EncodeUtils;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/5/22-9:50
 * Description:
 */
public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverContract.View {
    @BindView(R.id.banners_discover)
    LMBanners<TextView> bannersDiscover;
    @BindView(R.id.tv_yinmi_detail)
    TextView tvYinmiDetail;
    @BindView(R.id.tv_goplan_more)
    TextView tvGoplanMore;
    @BindView(R.id.tv_goplan)
    TextView tvGoplan;
    //本地图片BannerTop
    private ArrayList<Bitmap> localImagesDiscover = new ArrayList<Bitmap>();

    @Override
    protected DiscoverPresenter getPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discover, null);
        ButterKnife.bind(this, view);

        initView();

        setTranslucent();
        addBannerDiscover();
        initBannerDiscover(bannersDiscover);
        return view;
    }

    private void initView() {
        tvYinmiDetail.setOnClickListener(v -> YinMiDetailActivity.start(getActivity()));
    }


    private void initBannerDiscover(LMBanners bannersDiscover) {
        //设置Banners高度
//      bannersDiscover.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dip2px(getActivity(), 210)));
        bannersDiscover.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dp2px(getActivity(), 136)));
        //参数设置
        bannersDiscover.isGuide(false);//是否为引导页
        bannersDiscover.setAutoPlay(true);//自动播放
//        bannersDiscover.setAutoPlay(false);//自动播放
        bannersDiscover.setVertical(false);//是否锤子播放
        bannersDiscover.setScrollDurtion(1500);//两页切换时间
        bannersDiscover.setCanLoop(true);//循环播放
        bannersDiscover.setSelectIndicatorRes(R.drawable.discover_select);//选中的原点
        bannersDiscover.setUnSelectUnIndicatorRes(R.drawable.discover_unselect);//未选中的原点
        //若自定义原点到底部的距离,默认20,必须在setIndicatorWidth之前调用
        bannersDiscover.setIndicatorBottomPadding(5);
        bannersDiscover.setIndicatorWidth(5);//原点默认为5dp
        bannersDiscover.setHoriZontalTransitionEffect(TransitionEffect.Default);//选中喜欢的样式
//        bannersDiscover.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//自定义样式
        bannersDiscover.setDurtion(2000);//轮播切换时间
//        bannersDiscover.hideIndicatorLayout();//隐藏原点
//        bannersDiscover.showIndicatorLayout();//显示原点
        bannersDiscover.setIndicatorPosition(LMBanners.IndicaTorPosition.BOTTOM_MID);//设置原点显示位置


        //本地用法
        bannersDiscover.setAdapter(new LocalImgAdapter(getActivity(), LocalImgAdapter.TYPE_DISCOVER), localImagesDiscover);
        //网络图片
//        bannersDiscover.setAdapter(new UrlImgAdapter(MainActivity.this), networkImages);


    }

    private void addBannerDiscover() {
        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_DISCOVER_1))) {
            Bitmap bmp = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_DISCOVER_1));
            localImagesDiscover.add(bmp);
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.discover_1_1);
            localImagesDiscover.add(bmp);
        }

        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_DISCOVER_2))) {
            Bitmap bmp = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_DISCOVER_2));
            localImagesDiscover.add(bmp);
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.discover_1_2);
            localImagesDiscover.add(bmp);
        }
        if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_DISCOVER_3))) {
            Bitmap bmp = EncodeUtils.base64ToBitmap(PreferencesUtils.getString(getActivity(), PreferencesUtils.BITMAP_STRING_DISCOVER_3));
            localImagesDiscover.add(bmp);
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.discover_1_3);
            localImagesDiscover.add(bmp);
        }


    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
        QMUIStatusBarHelper.translucent(getActivity());
        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
    }


    @Override
    public void loading() {

    }

    @Override
    public void unLoad() {

    }

    @Override
    public void dealPaySuccess(VipDetailResp resp) {

    }

    @Override
    public void dealFailure(int code, String message) {

    }


    @OnClick({R.id.iv_vip, R.id.tv_vip_group, R.id.tv_insurance_more, R.id.tv_insurance, R.id.tv_merchant_more, R.id.tv_merchant,  R.id.tv_classroom_more,  R.id.tv_shared,R.id.tv_goplan_more, R.id.tv_goplan})
    public void onClick(View view) {
        if (isShowLoginDialog()) {
            return;
        }
        switch (view.getId()) {

            case R.id.iv_vip:
                if (!PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(getActivity());
                            }).show();
                    return;
                }
                FundGroupWebActivity.start(getActivity());
                break;

            case R.id.tv_vip_group:
                if (!PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(getActivity());
                            }).show();
                    return;
                }
                FundGroupWebActivity.start(getActivity());
                break;
/*            case R.id.tv_vip_fund:
                if (!PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(getActivity());
                            }).show();
                    return;
                }
                FundGroupWebActivity.start(getActivity());
                break;*/
            case R.id.tv_insurance_more:
                InsuranceWebActivity.start(getActivity());
                break;

            case R.id.tv_insurance:
                InsuranceWebActivity.start(getActivity());
                break;
            case R.id.tv_merchant_more:
                MerchantWebActivity.start(getActivity());
                break;
            case R.id.tv_merchant:
                MerchantWebActivity.start(getActivity());
                break;

            case R.id.tv_classroom_more:
                ClassroomWebActivity.start(getActivity());
                break;
            case R.id.tv_shared:
                SharedActivity.start(getActivity());
                break;
            case R.id.tv_goplan_more:
                SurveyActivity.start(getActivity());
                break;
            case R.id.tv_goplan:
                SurveyActivity.start(getActivity());
                break;
        }
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

}
