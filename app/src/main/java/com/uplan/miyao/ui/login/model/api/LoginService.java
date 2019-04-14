package com.uplan.miyao.ui.login.model.api;

import com.uplan.miyao.ui.login.model.LoginResp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Created by shilei on 2019/4/10-18:43
 * Description:
 */
public interface LoginService {

    final String REGIST = "/appClient/signinSignup/signin";

    @GET(REGIST)
    Observable<LoginResp> login(@Query("tel") String id, @Query("pwd") String key);
}
