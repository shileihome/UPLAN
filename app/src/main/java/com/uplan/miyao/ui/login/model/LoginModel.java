package com.uplan.miyao.ui.login.model;

import com.uplan.miyao.base.mvp.BaseModel;
import com.uplan.miyao.net.RxService;
import com.uplan.miyao.ui.login.contract.LoginContract;
import com.uplan.miyao.ui.login.api.LoginService;
import com.uplan.miyao.ui.login.model.resp.LoginResp;
import com.uplan.miyao.ui.login.model.resp.VerifyTelResp;

import io.reactivex.Observable;


public class LoginModel extends BaseModel implements LoginContract.Model {


    @Override
    public Observable<LoginResp> login(String phoneNum, String pwd) {
        return RxService.createApi(LoginService.class).login(phoneNum,pwd);
    }

    @Override
    public Observable<VerifyTelResp> verifyTel(String tel) {
        return RxService.createApi(LoginService.class).verifyTel(tel);
    }
}
