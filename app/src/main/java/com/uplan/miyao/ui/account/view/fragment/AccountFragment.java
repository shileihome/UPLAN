package com.uplan.miyao.ui.account.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.account.contract.AccountContract;
import com.uplan.miyao.ui.account.presenter.AccountPresenter;


public class AccountFragment extends BaseFragment<AccountPresenter> implements AccountContract.View {

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_account;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {

    }

    @Override
    protected AccountPresenter getPresenter() {
        return new AccountPresenter(this);
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
