package com.uplan.miyao.ui.account.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.account.contract.AccountContract;
import com.uplan.miyao.ui.account.model.AccountModel;
import com.uplan.miyao.ui.account.model.resp.AccountResp;
import com.uplan.miyao.util.RxUtils;


public class AccountPresenter extends BasePresenter<AccountContract.View, AccountContract.Model> {

    public AccountPresenter(AccountContract.View view) {
        super(view);
    }

    @Override
    protected AccountContract.Model getModel() {
        return new AccountModel();
    }


    public void getAccountInfo() {
       // mView.loading();
        mModel.getAccountInfo().compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<AccountResp>() {
            @Override
            public void onSuccess(AccountResp accountResp) {
           //     mView.unLoad();
                mView.dealGetAccountInfoSuccess(accountResp);
            }

            @Override
            public void onFailure(int code, String msg) {
          //      mView.unLoad();
                mView.dealFailure(code,msg);
            }
        });
    }

    public void logOut(String tel) {
        mView.loading();
        mModel.logOut(tel).compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<ResponseData>() {
            @Override
            public void onSuccess(ResponseData responseData) {
                mView.unLoad();
                mView.dealLogOutSuccess(responseData);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.unLoad();
                mView.dealFailure(code,msg);
            }
        });

    }
}
