package com.uplan.miyao.ui.account.api;

import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.account.model.resp.AccountResp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Created by shilei on 2019/4/29-16:33
 * Description:
 */
public interface AccountService {

    final String GETACCOUNT = "/appClient/getUserInfo/getInfo";
    final String LOGOUT="/appClient/signinSignup/signout";

    @GET(GETACCOUNT)
    Observable<AccountResp> getAccountInfo();

    @GET(LOGOUT)
    Observable<ResponseData> logOut(@Query("tel") String tel);

}
