package com.uplan.miyao.ui.login.presenter;

import android.util.Log;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.login.contract.ForgetPwdContract;
import com.uplan.miyao.ui.login.model.ForgetPwdModel;
import com.uplan.miyao.ui.login.model.resp.ForgetPwdResp;
import com.uplan.miyao.util.RxUtils;

/**
 * Author: Created by shilei on 2019/4/23-11:02
 * Description:
 */
public class ForgetPwdPresenter extends BasePresenter<ForgetPwdContract.View, ForgetPwdContract.Model> {

    public ForgetPwdPresenter(ForgetPwdContract.View view) {
        super(view);
    }

    @Override
    protected ForgetPwdContract.Model getModel() {
        return new ForgetPwdModel();
    }


    public void ModifyPwd(String phoneNum, String msgCode, String pwd) {
        mView.loading();
        mModel.ModifyPwd(phoneNum, msgCode, pwd).compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<ForgetPwdResp>() {


            @Override
            public void onSuccess(ForgetPwdResp forgetPwdResp) {
                mView.unLoad();
                Log.e("riri","我日");
                mView.dealModifySuccess(forgetPwdResp);
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.e("riri","ni日");
                mView.unLoad();
                mView.dealFailure(code, msg);

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
