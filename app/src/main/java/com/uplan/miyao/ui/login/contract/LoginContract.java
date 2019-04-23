package com.uplan.miyao.ui.login.contract;

import com.uplan.miyao.base.mvp.IModel;
import com.uplan.miyao.base.mvp.IView;
import com.uplan.miyao.ui.login.model.resp.LoginResp;

import io.reactivex.Observable;

public interface LoginContract {

    interface View extends IView {
        void loading();
        void unLoad();
        void dealLoginSuccess(LoginResp data);
    }

    interface Model extends IModel {
        Observable<LoginResp> login(String phoneNum, String pwd);
    }
}
