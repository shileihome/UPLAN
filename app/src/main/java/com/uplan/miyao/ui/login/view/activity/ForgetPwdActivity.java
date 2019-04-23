package com.uplan.miyao.ui.login.view.activity;

import com.uplan.miyao.base.mvp.BaseActivity;
import com.uplan.miyao.ui.login.contract.ForgetPwdContract;
import com.uplan.miyao.ui.login.model.resp.ForgetPwdResp;
import com.uplan.miyao.ui.login.presenter.ForgetPwdPresenter;

/**
 * Author: Created by shilei on 2019/4/23-11:00
 * Description:
 */
public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.View  {
    @Override
    protected void init() {

    }

    @Override
    protected ForgetPwdPresenter getPresenter() {
        return null;
    }

    @Override
    public void loading() {

    }

    @Override
    public void unLoad() {

    }

    @Override
    public void dealModifySuccess(ForgetPwdResp data) {

    }

    @Override
    public void dealFailure(int code, String message) {

    }
}
