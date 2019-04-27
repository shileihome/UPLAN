package com.uplan.miyao.ui.login.model;


import com.uplan.miyao.base.mvp.BaseModel;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.net.RxService;
import com.uplan.miyao.ui.login.api.ForgetPwdService;
import com.uplan.miyao.ui.login.contract.ForgetPwdContract;
import com.uplan.miyao.ui.login.model.resp.ForgetPwdResp;

import io.reactivex.Observable;

/**
 * Author: Created by shilei on 2019/4/23-11:07
 * Description:
 */
public class ForgetPwdModel extends  BaseModel implements ForgetPwdContract.Model {

    @Override
    public Observable<ForgetPwdResp> ModifyPwd(String phoneNum, String msgCode, String pwd) {
        return RxService.createApi(ForgetPwdService.class).ModifyPwd(phoneNum,msgCode, pwd);
    }

    public Observable<ResponseData> registVerificationCode(String tel) {
        return RxService.createApi(ForgetPwdService.class).registVerificationCode(tel);
    }
}