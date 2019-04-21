package com.uplan.miyao.ui.regist.model;

import com.uplan.miyao.base.mvp.BaseModel;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.net.RxService;
import com.uplan.miyao.ui.regist.api.RegistService;
import com.uplan.miyao.ui.regist.contract.RegistContract;
import com.uplan.miyao.ui.regist.model.resp.RegistResp;

import io.reactivex.Observable;


public class RegistModel extends BaseModel implements RegistContract.Model {

    @Override
    public Observable<RegistResp> regist(String phoneNum, String pwd,String verificationCode) {
        return RxService.createApi(RegistService.class).regist(phoneNum,pwd,verificationCode);
    }

    @Override
    public Observable<ResponseData> registVerificationCode(String tel) {
        return RxService.createApi(RegistService.class).registVerificationCode(tel);
    }
}
