package com.uplan.miyao.ui.regist.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.net.ResponseData;
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

    public void regist(String phoneNum, String pwd,String verificationCode) {
        mView.loading();
        mModel.regist(phoneNum, pwd,verificationCode).compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<RegistResp>() {

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

    public void registVerificationCode(String tel){
        mModel.registVerificationCode(tel).compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<ResponseData>() {
            @Override
            public void onSuccess(ResponseData responseData) {
                mView.delRegistVerificationCodeSucess(responseData);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.dealFailure(code,msg);
            }
        });

    }
}
