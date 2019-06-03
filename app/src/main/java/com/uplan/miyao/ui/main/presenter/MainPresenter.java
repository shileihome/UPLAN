package com.uplan.miyao.ui.main.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.ui.main.contract.MainContract;
import com.uplan.miyao.ui.main.model.MainModel;
import com.uplan.miyao.ui.main.model.resp.BannerInfoResp;
import com.uplan.miyao.util.RxUtils;


public class MainPresenter extends BasePresenter<MainContract.View, MainContract.Model> {

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    protected MainContract.Model getModel() {
        return new MainModel();
    }


    public void getBannerInfo() {
        // mView.loading();
        mModel.getBannerInfo().compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<BannerInfoResp>(){

            @Override
            public void onSuccess(BannerInfoResp bannerInfoResp) {
                mView.dealBannerSuccess(bannerInfoResp);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.dealFailure(code, msg);
            }
        });
    }
}
