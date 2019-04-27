package com.uplan.miyao.ui.vip.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.vip.contract.DiscoverContract;
import com.uplan.miyao.ui.vip.presenter.DiscoverPresenter;

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

    @Override
    protected DiscoverPresenter getPresenter() {
        return new DiscoverPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vip, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void dealFailure(int code, String message) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_shared, R.id.tv_join_vip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shared:
                break;
            case R.id.tv_join_vip:
                break;
        }
    }
}
