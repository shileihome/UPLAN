package com.uplan.miyao.ui.vip.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.login.view.activity.LoginActivity;
import com.uplan.miyao.ui.vip.contract.DiscoverContract;
import com.uplan.miyao.ui.vip.model.resp.VipDetailResp;
import com.uplan.miyao.ui.vip.presenter.DiscoverPresenter;
import com.uplan.miyao.ui.vip.view.activity.VipActivity;
import com.uplan.miyao.util.PreferencesUtils;
import com.uplan.miyao.widget.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class VipFragment extends BaseFragment<DiscoverPresenter> implements DiscoverContract.View {


    @BindView(R.id.iv_shared)
    ImageView ivShared;
    @BindView(R.id.tv_join_vip)
    TextView tvJoinVip;
    Unbinder unbinder;
    @BindView(R.id.ll_vip)
    CardView llVip;
    @BindView(R.id.ll_common)
    CardView llCommon;
    @BindView(R.id.tv_vip_lab)
    TextView tvVipLab;

    @Override
    protected DiscoverPresenter getPresenter() {
        return new DiscoverPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vip, null);
        unbinder = ButterKnife.bind(this, view);
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
            if (PreferencesUtils.getBoolean(getActivity(), PreferencesUtils.IS_ACTIVEA)) {
                llVip.setVisibility(View.VISIBLE);
                llCommon.setVisibility(View.GONE);
                tvVipLab.setText("尊敬的"+PreferencesUtils.getString(getActivity(),PreferencesUtils.USER_TEL)+"，您好");
            } else {
                llCommon.setVisibility(View.VISIBLE);
                llVip.setVisibility(View.GONE);
            }
        } else {
            llCommon.setVisibility(View.VISIBLE);
            llVip.setVisibility(View.GONE);
        }
    }

    @Override
    public void dealFailure(int code, String message) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_join_vip, R.id.ll_vip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_join_vip:
                if(PreferencesUtils.getBoolean(getActivity(),PreferencesUtils.LOGIN_STATE)){
                   VipActivity.start(getActivity());
                }else{
                    CommonDialog commonDialog = new CommonDialog(getActivity()).builder();
                    commonDialog.setSubMessage("请先登录!").
                            setLeftButton(getString(R.string.common_dialog_cancel), v -> {

                            }).
                            setRightButton(getString(R.string.commit_change), v -> {

                                LoginActivity.start(getActivity());
                            }).show();
                }
                break;
            case R.id.ll_vip:
                VipActivity.start(getActivity());
                break;
        }
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
}
