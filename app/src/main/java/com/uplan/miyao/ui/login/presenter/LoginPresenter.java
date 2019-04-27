package com.uplan.miyao.ui.login.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.ui.login.contract.LoginContract;
import com.uplan.miyao.ui.login.model.LoginModel;
import com.uplan.miyao.ui.login.model.resp.LoginResp;
import com.uplan.miyao.util.RxUtils;


public class LoginPresenter extends BasePresenter<LoginContract.View, LoginContract.Model> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    protected LoginContract.Model getModel() {
        return new LoginModel();
    }


    public void login(String phoneNum, String pwd) {
        mView.loading();
        mModel.login(phoneNum, pwd).compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<LoginResp>() {


            @Override
            public void onSuccess(LoginResp loginResp) {
                mView.unLoad();
                mView.dealLoginSuccess(loginResp);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.unLoad();
                mView.dealFailure(code,msg);

            }
        });
    }
}
