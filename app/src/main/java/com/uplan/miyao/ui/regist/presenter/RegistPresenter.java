package com.uplan.miyao.ui.regist.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.ui.regist.contract.RegistContract;
import com.uplan.miyao.ui.regist.model.RegistModel;
import com.uplan.miyao.ui.regist.model.resp.RegistResp;
import com.uplan.miyao.util.RxUtils;


public class RegistPresenter extends BasePresenter<RegistContract.View, RegistContract.Model> {

    public RegistPresenter(RegistContract.View view) {
        super(view);
    }

    @Override
    protected RegistContract.Model getModel() {
        return new RegistModel();
    }

    public void regist(String phoneNum, String pwd) {
        mView.loading();
        mModel.regist(phoneNum, pwd).compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<RegistResp>() {

            @Override
            public void onSuccess(RegistResp registResp) {
                mView.unLoad();
                mView.dealRegistSuccess(registResp);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.unLoad();
                mView.dealFailure(code,msg);
            }
        });
    }
}
