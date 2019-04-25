package com.uplan.miyao.ui.login.api;

import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.login.model.resp.ForgetPwdResp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Created by shilei on 2019/4/24-9:34
 * Description:
 */
public interface ForgetPwdService {

    final String FORGETPWD = "/appClient/signinSignup/resetpwd";
    final String REGIST_VERIFICATION_CODE = "/appClient/utils/sendmsg";

    @GET(FORGETPWD)
    Observable<ForgetPwdResp> ModifyPwd(@Query("tel") String tel, @Query("msgCode") String msgCode , @Query("pwd") String pwd);


    @GET(REGIST_VERIFICATION_CODE)
    Observable<ResponseData> registVerificationCode(@Query("tel") String tel);
}


