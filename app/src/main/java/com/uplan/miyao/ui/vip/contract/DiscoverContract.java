package com.uplan.miyao.ui.vip.contract;

import com.uplan.miyao.base.mvp.IModel;
import com.uplan.miyao.base.mvp.IView;
import com.uplan.miyao.ui.vip.model.resp.VipDetailResp;

import io.reactivex.Observable;

public interface DiscoverContract {

    interface View extends IView{

    void loading();

    void unLoad();

    void dealPaySuccess(VipDetailResp resp);
}

interface Model extends IModel {
    Observable<VipDetailResp> pay();
}
}
