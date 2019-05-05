package com.uplan.miyao.ui.login.api;

import com.uplan.miyao.ui.login.model.resp.LoginResp;
import com.uplan.miyao.ui.login.model.resp.VerifyTelResp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Created by shilei on 2019/4/10-18:43
 * Description:
 */
public interface LoginService {

    final String LOGIN = "/appClient/signinSignup/signin";
    final String VERIFY_TEL="/appClient/signinSignup/checkTel";



    @GET(LOGIN)
    Observable<LoginResp> login(@Query("tel") String id, @Query("pwd") String key);

    @GET(VERIFY_TEL)
    Observable<VerifyTelResp> verifyTel(@Query("tel") String tel);

}
