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
import com.uplan.miyao.ui.financial.view.FinancialActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FinancialFragment extends BaseFragment<FinancialPresenter> implements FinancialContract.View {

    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_about)
    TextView tvAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financial, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected FinancialPresenter getPresenter() {
        return new FinancialPresenter(this);
    }

/*
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void killMyself() {
        finish();
    }
*/

    @Override
    public void dealFailure(int code, String message) {

    }

    @OnClick({R.id.tv_start, R.id.tv_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                FinancialActivity.start(getActivity());
                break;
            case R.id.tv_about:
                break;
        }
    }
}
