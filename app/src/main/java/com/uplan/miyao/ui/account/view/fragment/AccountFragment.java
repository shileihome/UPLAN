package com.uplan.miyao.ui.account.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.account.contract.AccountContract;
import com.uplan.miyao.ui.account.model.resp.AccountResp;
import com.uplan.miyao.ui.account.presenter.AccountPresenter;
import com.uplan.miyao.ui.account.view.activity.BuyActivity;
import com.uplan.miyao.ui.account.view.activity.HelpCenterActivity;
import com.uplan.miyao.ui.account.view.activity.HoldActivity;
import com.uplan.miyao.ui.account.view.activity.Mix50Activity;
import com.uplan.miyao.ui.account.view.activity.RecordActivity;
import com.uplan.miyao.ui.account.view.activity.RedeemActivity;
import com.uplan.miyao.ui.account.view.activity.RemindActivity;
import com.uplan.miyao.ui.account.view.activity.RiskEvaluationActivity;
import com.uplan.miyao.ui.account.view.activity.SettingActivity;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.main.view.activity.MainActivity;
import com.uplan.miyao.ui.survey.view.SurveyActivity;
import com.uplan.miyao.ui.vip.view.activity.VipActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.util.StringUtils;
import com.uplan.miyao.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AccountFragment extends BaseFragment<AccountPresenter> implements AccountContract.View {

    @BindView(R.id.tv_setting)
    TextView tvSetting;

    @BindView(R.id.tv_login_name)
    TextView tvLoginName;
    @BindView(R.id.iv_vip_logo)
    ImageView ivVipLogo;
    @BindView(R.id.account_bar)
    LinearLayout accountBar;
    @BindView(R.id.tv_honav2)
    TextView tvHonav2;
    @BindView(R.id.tv_honav3)
    TextView tvHonav3;
    @BindView(R.id.iv_holist2)
    ImageView ivHolist2;
    @BindView(R.id.rl_holist2)
    RelativeLayout rlHolist2;
    @BindView(R.id.iv_holist3)
    ImageView ivHolist3;
    @BindView(R.id.rl_holist3)
    RelativeLayout rlHolist3;
    @BindView(R.id.iv_holist5)
    ImageView ivHolist5;
    @BindView(R.id.rl_holist5)
    RelativeLayout rlHolist5;
    @BindView(R.id.iv_holist4)
    ImageView ivHolist4;
    @BindView(R.id.rl_holist4)
    RelativeLayout rlHolist4;
    @BindView(R.id.iv_holist1)
    ImageView ivHolist1;
    @BindView(R.id.rl_holist1)
    RelativeLayout rlHolist1;
    /*  @BindView(R.id.iv_holist7)
      ImageView ivHolist7;
      @BindView(R.id.rl_holist7)
      RelativeLayout rlHolist7;*/
    @BindView(R.id.iv_holist8)
    ImageView ivHolist8;
    @BindView(R.id.rl_holist8)
    RelativeLayout rlHolist8;
    @BindView(R.id.tv_general_assets)
    TextView tvGeneralAssets;
    @BindView(R.id.tv_up_down)
    TextView tvUpDown;
    @BindView(R.id.tv_general_up_down)
    TextView tvGeneralUpDown;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.ll_title_background)
    LinearLayout llTitleBackground;
    @BindView(R.id.tv_notify_num)
    TextView tvNotifyNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        ButterKnife.bind(this, view);
        mPresenter = getPresenter();
        initView();
        setTranslucent();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initData();
    }

    private void initView() {
        if (PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.LOGIN_STATE)) {
            tvLoginName.setText(StringUtils.formatTel(PreferencesUtils.getString(getActivity(), PreferencesUtils.USER_TEL)));
            if (PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                ivVipLogo.setVisibility(View.VISIBLE);
                ivVipLogo.setImageResource(R.drawable.vip_logo);
                llTitleBackground.setBackgroundColor(Color.parseColor("#FFC69D5B"));
            } else {
                ivVipLogo.setVisibility(View.VISIBLE);
                ivVipLogo.setImageResource(R.drawable.bug_sign);
                llTitleBackground.setBackgroundColor(Color.parseColor("#FF31BCE9"));
            }
        } else {
            tvLoginName.setText("未登录");
            ivVipLogo.setVisibility(View.GONE);
        }


    }

    int num = 0;

    private void initData() {

        if (PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.LOGIN_STATE)) {
            mPresenter.getAccountInfo();
        }

    }

    @Override
    protected AccountPresenter getPresenter() {
        return new AccountPresenter(this);
    }


    @Override
    public void dealFailure(int code, String message) {

    }


    @OnClick({R.id.tv_setting, R.id.ll_account, R.id.tv_honav2, R.id.tv_honav3, R.id.rl_holist1, R.id.rl_holist2,
            R.id.rl_holist3, R.id.rl_holist4, R.id.rl_holist5, R.id.rl_holist8, R.id.ll_mix50, R.id.tv_login_out})
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.tv_setting:
                if (isShowLoginDialog()) {
                    return;
                }
                SettingActivity.start(getActivity());
                break;
            case R.id.ll_account:
                if (!isLogined()) {
                    LoginActivity.start(getActivity());
                } else {
                    VipActivity.start(getActivity());
                    return;
                }
                break;
            /*    case R.id.iv_login:
             *//* if(!isLogined()){
                    LoginActivity.start(getActivity());
                }else{

                    return;
                }*//*

                break;
            case R.id.tv_login_name:
               *//* if(!isLogined()){
                    LoginActivity.start(getActivity());
                }else{

                    return;
                }*//*
                break;*/
            case R.id.tv_honav2:
                if (isShowLoginDialog()) {
                    return;
                }
                BuyActivity.start(getActivity());
                break;
            case R.id.tv_honav3:
                if (isShowLoginDialog()) {
                    return;
                }
                RedeemActivity.start(getActivity());
                break;
            case R.id.rl_holist1:
                if (isShowLoginDialog()) {
                    return;
                }
                SurveyActivity.start(getActivity());
                break;
            case R.id.rl_holist2:
                if (isShowLoginDialog()) {
                    return;
                }
                HoldActivity.start(getActivity());
                break;
            case R.id.rl_holist3:
                if (isShowLoginDialog()) {
                    return;
                }
                RecordActivity.start(getActivity());
                break;
            case R.id.rl_holist4:
                if (isShowLoginDialog()) {
                    return;
                }
                RiskEvaluationActivity.start(getActivity());
                break;
            case R.id.rl_holist5:
                if (isShowLoginDialog()) {
                    return;
                }
                RemindActivity.start(getActivity());
                break;

   /*         case R.id.rl_holist7:
                if(isShowLoginDialog()){
                    return;
                }
                //我的邀请
                break;*/
            case R.id.rl_holist8:
                if (isShowLoginDialog()) {
                    return;
                }
                HelpCenterActivity.start(getActivity());
                break;
            case R.id.ll_mix50:
                //mix50 事件入口
                Mix50Activity.start(getActivity());
                break;
            case R.id.tv_login_out:
                if (isShowLoginDialog()) {
                    return;
                }
                mPresenter.logOut(PreferencesUtils.getString(getActivity(), PreferencesUtils.USER_TEL));
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

    @Override
    public void loading() {
        showLoadingDialog();
    }

    @Override
    public void unLoad() {
        hideLoadingDialog();
    }

    @Override
    public void dealLogOutSuccess(ResponseData responseData) {
        PreferencesUtils.putBoolean(getActivity(), PreferencesUtils.LOGIN_STATE, false);
        PreferencesUtils.putString(getActivity(), PreferencesUtils.PLAY_SESSION, "");
        PreferencesUtils.putString(getActivity(), PreferencesUtils.USER_NAME, "未登录");
//        PreferencesUtils.putString(getActivity(), PreferencesUtils.USER_TEL, "");
        PreferencesUtils.putBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA, false);
        PreferencesUtils.putLong(getActivity(), PreferencesUtils.EXPIRE_TIME, 0);
        PreferencesUtils.putString(getActivity(), PreferencesUtils.MESSAGE_NUM, "0");
        tvLoginName.setText("未登录");
        tvGeneralAssets.setText("---");
        tvUpDown.setText("---");
        tvGeneralUpDown.setText("---");
        ((MainActivity) getActivity()).setSelectItem(((MainActivity) getActivity()).financialLayout);
       if( ((MainActivity) getActivity()).getHomeFragment()!=null){
           ((MainActivity) getActivity()).getHomeFragment().onStart();
       }
    }

    @Override
    public void dealGetAccountInfoSuccess(AccountResp accountResp) {
        if (accountResp != null && accountResp.data != null && accountResp.data.size() > 0) {
            tvGeneralAssets.setText(accountResp.data.get(0).porperty + "");
            tvUpDown.setText(accountResp.data.get(0).previousProfit + "");
            tvGeneralUpDown.setText(accountResp.data.get(0).accumulatedProfit + "");
            if (!TextUtils.isEmpty(accountResp.data.get(0).message_num)) {
                PreferencesUtils.putString(getActivity(), PreferencesUtils.MESSAGE_NUM, accountResp.data.get(0).message_num);
            } else {
                PreferencesUtils.putString(getActivity(), PreferencesUtils.MESSAGE_NUM, "0");
            }
            if (Integer.parseInt(accountResp.data.get(0).is_active.trim()) == 1) {

                PreferencesUtils.putBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA, true);
            } else {
                PreferencesUtils.putBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA, false);
            }
        }

        String num2 = PreferencesUtils.getString(getActivity(), PreferencesUtils.MESSAGE_NUM);
        if (Integer.parseInt(num2.trim()) > 0 && num2.trim().length() < 3) {
            tvNotifyNum.setVisibility(View.VISIBLE);
            tvNotifyNum.setText(num2);

        } else if (Integer.parseInt(num2.trim()) > 0 && num2.trim().length() >= 3) {
            tvNotifyNum.setVisibility(View.VISIBLE);
            tvNotifyNum.setText("..");
        } else {
            tvNotifyNum.setVisibility(View.GONE);
        }
        initView();

    }

    CommonDialog infoDialog;

    @Override
    public void dealGetAccountInfoFailure(int code, String message) {
        if (code == 1) {

            if (infoDialog != null && infoDialog.isShow()) {
                return;
            }

            PreferencesUtils.putBoolean(getActivity(), PreferencesUtils.LOGIN_STATE, false);
            PreferencesUtils.putString(getActivity(), PreferencesUtils.PLAY_SESSION, "");
            PreferencesUtils.putString(getActivity(), PreferencesUtils.USER_NAME, "未登录");
//        PreferencesUtils.putString(getActivity(), PreferencesUtils.USER_TEL, "");
            PreferencesUtils.putBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA, false);
            PreferencesUtils.putLong(getActivity(), PreferencesUtils.EXPIRE_TIME, 0);
            PreferencesUtils.putString(getActivity(), PreferencesUtils.MESSAGE_NUM, "0");
            tvLoginName.setText("未登录");
            tvGeneralAssets.setText("---");
            tvUpDown.setText("---");
            tvGeneralUpDown.setText("---");

            infoDialog = new CommonDialog(getActivity()).builder();
            infoDialog.setSubMessage(message).
                    setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                        if (((MainActivity) getActivity()).getHomeFragment() != null) {
                            ((MainActivity) getActivity()).getHomeFragment().onResume();
                        }
                    }).
                    setRightButton(getString(R.string.commit_change), v -> {
                        LoginActivity.start(getActivity());
                    }).show();
        }


    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
        QMUIStatusBarHelper.translucent(getActivity());
//        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
    }


}
