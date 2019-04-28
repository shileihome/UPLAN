package com.uplan.miyao.ui.vip.model;

import com.uplan.miyao.base.mvp.BaseModel;
import com.uplan.miyao.net.RxService;
import com.uplan.miyao.ui.vip.api.DiscoverService;
import com.uplan.miyao.ui.vip.contract.DiscoverContract;
import com.uplan.miyao.ui.vip.model.resp.VipDetailResp;

import io.reactivex.Observable;


public class DiscoverModel extends BaseModel implements DiscoverContract.Model {

    @Override
    public Observable<VipDetailResp> pay() {
        return RxService.createApi(DiscoverService.class).pay();
    }
}
