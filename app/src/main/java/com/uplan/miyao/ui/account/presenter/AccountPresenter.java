package com.uplan.miyao.ui.account.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.ui.account.contract.AccountContract;
import com.uplan.miyao.ui.account.model.AccountModel;


public class AccountPresenter extends BasePresenter<AccountContract.View, AccountContract.Model> {

    public AccountPresenter(AccountContract.View view) {
        super(view);
    }

    @Override
    protected AccountContract.Model getModel() {
        return new AccountModel();
    }

}
