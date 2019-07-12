package com.uplan.miyao.ui.main.model;

import com.uplan.miyao.base.mvp.BaseModel;
import com.uplan.miyao.net.RxService;
import com.uplan.miyao.ui.main.api.MainService;
import com.uplan.miyao.ui.main.contract.MainContract;
import com.uplan.miyao.ui.main.model.resp.BannerInfoResp;
import com.uplan.miyao.ui.main.model.resp.VersionResp;

import io.reactivex.Observable;


public class MainModel extends BaseModel implements MainContract.Model {



    @Override
    public Observable<BannerInfoResp> getBannerInfo() {
        return RxService.createApi(MainService.class).getBannerInfo();
    }

    @Override
    public Observable<VersionResp> getVersionCode() {
        return RxService.createApi(MainService.class).getVersionCode();
    }
}
