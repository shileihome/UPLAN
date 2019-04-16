package com.uplan.miyao.ui.account.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.uplan.miyao.ui.account.view.activity.VIPEquityActivity;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.main.view.activity.MainActivity;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected AccountPresenter getPresenter() {
        return new AccountPresenter(this);
    }


    @Override
    public void dealFailure(int code, String message) {

    }


    @OnClick({R.id.tv_setting, R.id.iv_login, R.id.tv_login_name,R.id.tv_honav1, R.id.tv_honav2, R.id.tv_honav3, R.id.tv_honav4, R.id.tv_honav5, R.id.rl_holist1, R.id.rl_holist2, R.id.rl_holist3, R.id.rl_holist4, R.id.rl_holist5, R.id.rl_holist6, R.id.rl_holist7})
    public void onClick(View view) {
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
            case R.id.tv_honav1:
                break;
            case R.id.tv_honav2:
                ((MainActivity)getActivity()).setSelectItem( ((MainActivity)getActivity()).financialLayout);
                break;
            case R.id.tv_honav3:
                RedeemActivity.start(getActivity());
                break;
            case R.id.tv_honav4:
                break;
            case R.id.tv_honav5:
                break;
            case R.id.rl_holist1:
                ((MainActivity)getActivity()).setSelectItem( ((MainActivity)getActivity()).surveyLayout);
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
            case R.id.rl_holist6:
                VIPEquityActivity.start(getActivity());
                break;
            case R.id.rl_holist7:
                HelpCenterActivity.start(getActivity());
                break;
        }
    }
}
