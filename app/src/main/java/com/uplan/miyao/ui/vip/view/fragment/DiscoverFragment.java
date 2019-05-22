package com.uplan.miyao.ui.vip.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIStatusBarHelper;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.vip.contract.DiscoverContract;
import com.uplan.miyao.ui.vip.model.resp.VipDetailResp;
import com.uplan.miyao.ui.vip.presenter.DiscoverPresenter;
import com.uplan.miyao.ui.vip.view.activity.ClassroomWebActivity;
import com.uplan.miyao.ui.vip.view.activity.FinanceWebActivity;
import com.uplan.miyao.ui.vip.view.activity.FundGroupWebActivity;
import com.uplan.miyao.ui.vip.view.activity.InsuranceWebActivity;
import com.uplan.miyao.ui.vip.view.activity.MerchantWebActivity;
import com.uplan.miyao.ui.vip.view.activity.SharedActivity;
import com.uplan.miyao.ui.vip.view.activity.VipActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Created by shilei on 2019/5/22-9:50
 * Description:
 */
public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverContract.View {
    @BindView(R.id.tv_finance_text)
    TextView tvFinanceText;
    @BindView(R.id.tv_finance_time)
    TextView tvFinanceTime;
    @BindView(R.id.tv_classroom_title)
    TextView tvClassroomTitle;
    @BindView(R.id.tv_classroom_text)
    TextView tvClassroomText;
    @BindView(R.id.tv_classroom_time)
    TextView tvClassroomTime;

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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {

    }

    public void setTranslucent() {
        QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
        QMUIStatusBarHelper.translucent(getActivity());
//        QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
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


    @OnClick({R.id.iv_vip, R.id.tv_vip_group, R.id.tv_vip_fund, R.id.tv_insurance, R.id.tv_merchant, R.id.text_finance_more, R.id.tv_finance, R.id.tv_classroom_more, R.id.tv_classroom, R.id.tv_shared})
    public void onClick(View view) {
        if (isShowLoginDialog()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_vip:
                if(!PreferencesUtils.getBoolean(getActivity(),PreferencesUtils.IS_ACTIVEA)){
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先成为会员!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {
                            }).
                            setRightButton(getString(R.string.commit_change), v -> {
                                VipActivity.start(getActivity());
                            }).show();
                    return;
                }
                VipActivity.start(getActivity());
                break;
            case R.id.tv_vip_group:
                FundGroupWebActivity.start(getActivity());
                break;
            case R.id.tv_vip_fund:
                FundGroupWebActivity.start(getActivity());
                break;
            case R.id.tv_insurance:
                InsuranceWebActivity.start(getActivity());
                break;
            case R.id.tv_merchant:
                MerchantWebActivity.start(getActivity());
                break;
            case R.id.text_finance_more:
                FinanceWebActivity.start(getActivity());
                break;
            case R.id.tv_finance:
                FinanceWebActivity.start(getActivity());
                break;
            case R.id.tv_classroom_more:
                ClassroomWebActivity.start(getActivity());
                break;
            case R.id.tv_classroom:
                ClassroomWebActivity.start(getActivity());
                break;
            case R.id.tv_shared:
                SharedActivity.start(getActivity());
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
