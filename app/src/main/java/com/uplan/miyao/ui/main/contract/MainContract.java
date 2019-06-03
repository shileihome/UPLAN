package com.uplan.miyao.ui.main.contract;

import com.uplan.miyao.base.mvp.IModel;
import com.uplan.miyao.base.mvp.IView;
import com.uplan.miyao.ui.main.model.resp.BannerInfoResp;

import io.reactivex.Observable;


public interface MainContract {

    interface View extends IView {
        void dealBannerSuccess(BannerInfoResp resp);

    }

    interface Model extends IModel {
        Observable<BannerInfoResp> getBannerInfo();

    }
}
