package com.uplan.miyao.ui.financial.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.financial.contract.FinancialContract;
import com.uplan.miyao.ui.financial.presenter.FinancialPresenter;


public class FinancialFragment extends BaseFragment<FinancialPresenter> implements FinancialContract.View {

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_financial;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {

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
}
