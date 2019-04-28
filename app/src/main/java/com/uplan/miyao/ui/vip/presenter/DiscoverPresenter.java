package com.uplan.miyao.ui.vip.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.ui.vip.contract.DiscoverContract;
import com.uplan.miyao.ui.vip.model.DiscoverModel;
import com.uplan.miyao.ui.vip.model.resp.VipDetailResp;
import com.uplan.miyao.util.RxUtils;


public class DiscoverPresenter extends BasePresenter<DiscoverContract.View, DiscoverContract.Model> {

    public DiscoverPresenter(DiscoverContract.View view) {
        super(view);
    }

    @Override
    protected DiscoverContract.Model getModel() {
        return new DiscoverModel();
    }

    public void pay(){

        mView.loading();
        mModel.pay().compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<VipDetailResp>() {

            @Override
            public void onSuccess(VipDetailResp resp) {
                mView.unLoad();
                mView.dealPaySuccess(resp);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.unLoad();
                mView.dealFailure(code,msg);
            }
        });
    }

}
