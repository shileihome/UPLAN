package com.uplan.miyao.ui.regist.api;

import com.uplan.miyao.ui.regist.model.resp.RegistResp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Created by shilei on 2019/4/10-18:43
 * Description:
 */
public interface RegistService {

    final String REGIST = "/appClient/signinSignup/signup";

    @GET(REGIST)
    Observable<RegistResp> regist(@Query("tel") String id, @Query("pwd") String key);
}
