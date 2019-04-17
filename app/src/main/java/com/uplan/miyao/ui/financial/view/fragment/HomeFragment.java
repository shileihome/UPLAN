package com.uplan.miyao.ui.financial.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.financial.contract.FinancialContract;
import com.uplan.miyao.ui.financial.presenter.FinancialPresenter;
import com.uplan.miyao.ui.financial.view.activity.FinancialActivity;
import com.uplan.miyao.ui.financial.view.activity.SafeActivity;
import com.uplan.miyao.ui.financial.view.activity.TeamInfoActivity;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment<FinancialPresenter> implements FinancialContract.View {

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_safe)
    TextView tvSafe;
    @BindView(R.id.tv_team_info)
    TextView tvTeamInfo;

    boolean loginState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginState= PreferencesUtils.getBoolean(getActivity(),PreferencesUtils.LOGIN_STATE);
        if(loginState){
            tvLogin.setVisibility(View.GONE);
        }else{
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


    @OnClick({R.id.tv_login,R.id.tv_financial, R.id.tv_safe, R.id.tv_team_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                LoginActivity.start(getActivity());
                break;
            case R.id.tv_financial:
                if(loginState){
                    FinancialActivity.start(getActivity());
                }else{
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先登陆!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {

                            }).
                            setRightButton(getString(R.string.commit_change), v -> {

                            LoginActivity.start(getActivity());
                            }).show();
                }
                break;
            case R.id.tv_safe:
                SafeActivity.start(getActivity());
                break;
            case R.id.tv_team_info:
                TeamInfoActivity.start(getActivity());
                break;
        }
    }
}
