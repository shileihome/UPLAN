package com.uplan.miyao.ui.account.contract;

import com.uplan.miyao.base.mvp.IModel;
import com.uplan.miyao.base.mvp.IView;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.account.model.resp.AccountResp;

import io.reactivex.Observable;


public interface AccountContract {

    interface View extends IView {
        void loading();
        void unLoad();
        void dealLogOutSuccess(ResponseData responseData);
        void dealGetAccountInfoSuccess(AccountResp accountResp);
        void dealGetAccountInfoFailure(int code, String message);
        void dealReServiceLogoutSuccess(ResponseData responseData,String reserviceCode);
        void dealReServiceLogoutFailure(int code, String message,String reserviceCode);
    }

    interface Model extends IModel {
        Observable<AccountResp> getAccountInfo();
        Observable<ResponseData> logOut(String tel);
    }
}
