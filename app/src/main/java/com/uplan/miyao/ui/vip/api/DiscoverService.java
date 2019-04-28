package com.uplan.miyao.ui.vip.api;

import com.uplan.miyao.ui.vip.model.resp.VipDetailResp;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author: Created by shilei on 2019/4/10-18:43
 * Description:
 */
public interface DiscoverService {

    final String PAY = "/APPClient/wechatPay/getprepayid?vipType=1";

    @GET(PAY)
    Observable<VipDetailResp> pay();


}
