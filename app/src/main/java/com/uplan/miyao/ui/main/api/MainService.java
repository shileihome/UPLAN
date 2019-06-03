package com.uplan.miyao.ui.main.api;

import com.uplan.miyao.ui.main.model.resp.BannerInfoResp;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author: Created by shilei on 2019/6/3-19:17
 * Description:
 */
public interface MainService {

    final String GET_BANNER_INFO="/appClient/signinSignup/signout";

    @GET(GET_BANNER_INFO)
    Observable<BannerInfoResp> getBannerInfo();

}
