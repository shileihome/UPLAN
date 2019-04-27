package com.uplan.miyao.ui.account.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.account.contract.AccountContract;
import com.uplan.miyao.ui.account.presenter.AccountPresenter;
import com.uplan.miyao.ui.account.view.activity.HelpCenterActivity;
import com.uplan.miyao.ui.account.view.activity.HoldActivity;
import com.uplan.miyao.ui.account.view.activity.RecordActivity;
import com.uplan.miyao.ui.account.view.activity.RedeemActivity;
import com.uplan.miyao.ui.account.view.activity.RemindActivity;
import com.uplan.miyao.ui.account.view.activity.RiskEvaluationActivity;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.main.view.activity.MainActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AccountFragment extends BaseFragment<AccountPresenter> implements AccountContract.View {

    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.iv_login)
    ImageView ivLogin;
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
    @BindView(R.id.iv_holist7)
    ImageView ivHolist7;
    @BindView(R.id.rl_holist7)
    RelativeLayout rlHolist7;
    @BindView(R.id.iv_holist8)
    ImageView ivHolist8;
    @BindView(R.id.rl_holist8)
    RelativeLayout rlHolist8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        if (PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.LOGIN_STATE)) {
            tvLoginName.setText(PreferencesUtils.getString(getActivity(), PreferencesUtils.USER_NAME));
            if (PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                ivVipLogo.setVisibility(View.VISIBLE);

            } else {
                ivVipLogo.setVisibility(View.GONE);
            }
        } else {
            tvLoginName.setText("未登陆");
            ivVipLogo.setVisibility(View.GONE);
        }
    }

    @Override
    protected AccountPresenter getPresenter() {
        return new AccountPresenter(this);
    }


    @Override
    public void dealFailure(int code, String message) {

    }


    @OnClick({R.id.tv_setting, R.id.iv_login, R.id.tv_login_name, R.id.tv_honav2, R.id.tv_honav3, R.id.rl_holist1, R.id.rl_holist2, R.id.rl_holist3, R.id.rl_holist4, R.id.rl_holist5, R.id.rl_holist7, R.id.rl_holist8})
    public void onClick(View view) {

        if (!isLogined()) {
            CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
            commonDialog.setSubMessage("请先登陆!").
                    setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                    }).
                    setRightButton(getString(R.string.commit_change), v -> {
                        LoginActivity.start(getActivity());
                    }).show();
            return;
        }

        switch (view.getId()) {
            case R.id.tv_setting:
                Toast.makeText(getActivity(), "设置", Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_login:
                LoginActivity.start(getActivity());
                break;
            case R.id.tv_login_name:
                LoginActivity.start(getActivity());
                break;
            case R.id.tv_honav2:
                ((MainActivity) getActivity()).setSelectItem(((MainActivity) getActivity()).financialLayout);
                break;
            case R.id.tv_honav3:
                RedeemActivity.start(getActivity());
                break;
            case R.id.rl_holist1:
                ((MainActivity) getActivity()).setSelectItem(((MainActivity) getActivity()).surveyLayout);
                break;
            case R.id.rl_holist2:
                HoldActivity.start(getActivity());
                break;
            case R.id.rl_holist3:
                RecordActivity.start(getActivity());
                break;
            case R.id.rl_holist4:
                RiskEvaluationActivity.start(getActivity());
                break;
            case R.id.rl_holist5:
                RemindActivity.start(getActivity());
                break;

            case R.id.rl_holist7:
                //我的邀请
                break;
            case R.id.rl_holist8:
                HelpCenterActivity.start(getActivity());
                break;
        }
    }

    private boolean isLogined() {
        return PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.LOGIN_STATE);
    }

}
