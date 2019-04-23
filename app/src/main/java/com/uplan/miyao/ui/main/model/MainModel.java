package com.uplan.miyao.ui.main.model;

import com.uplan.miyao.base.mvp.BaseModel;
import com.uplan.miyao.net.RxService;
import com.uplan.miyao.ui.main.contract.MainContract;
import com.uplan.miyao.ui.main.model.api.MainService;
import com.uplan.miyao.ui.main.model.resp.StatusCountResp;
import com.uplan.miyao.ui.main.model.resp.UserInfoResp;

import io.reactivex.Observable;


public class MainModel extends BaseModel implements MainContract.Model {


    @Override
    public Observable<UserInfoResp> getUserInfo() {
        return RxService.createApi(MainService.class).getUserInfo();
    }

    @Override
    public Observable<StatusCountResp> getStatusCount() {
        return RxService.createApi(MainService.class).getStatusCount();
    }
}
