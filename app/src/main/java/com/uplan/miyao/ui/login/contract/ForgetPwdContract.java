package com.uplan.miyao.ui.login.contract;

import com.uplan.miyao.base.mvp.IModel;
import com.uplan.miyao.base.mvp.IView;
import com.uplan.miyao.ui.login.model.resp.ForgetPwdResp;

import io.reactivex.Observable;

/**
 * Author: Created by shilei on 2019/4/23-11:09
 * Description:
 */
public interface ForgetPwdContract {
    interface View extends IView {
        void loading();
        void unLoad();
        void dealModifySuccess(ForgetPwdResp data);
    }

    interface Model extends IModel {
        Observable<ForgetPwdResp> ModifyPwd(String phoneNum, String msgCode, String pwd);
    }
}
