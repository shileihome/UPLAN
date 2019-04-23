package com.uplan.miyao.ui.login.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
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


    public void login(String phoneNum, String msgCode, String pwd) {
        mView.loading();
        mModel.ModifyPwd(phoneNum, msgCode, pwd).compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<ForgetPwdResp>() {


            @Override
            public void onSuccess(ForgetPwdResp forgetPwdResp) {
                mView.unLoad();
                mView.dealModifySuccess(forgetPwdResp);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.unLoad();
                mView.dealFailure(code, msg);

            }
        });
    }
}
