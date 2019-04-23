package com.uplan.miyao.ui.login.api;

import com.uplan.miyao.ui.login.model.resp.ForgetPwdResp;
import com.uplan.miyao.ui.login.model.resp.LoginResp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Created by shilei on 2019/4/10-18:43
 * Description:
 */
public interface LoginService {

    final String REGIST = "/appClient/signinSignup/signin";
    final String FORGETPWD = "/appClient/signinSignup/resetpwd";

    @GET(REGIST)
    Observable<LoginResp> login(@Query("tel") String id, @Query("pwd") String key);

    @GET(FORGETPWD)
    Observable<ForgetPwdResp> ModifyPwd(@Query("tel") String tel,@Query("msgCode") String msgCode ,@Query("pwd") String pwd);
}
