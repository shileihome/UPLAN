package com.uplan.miyao.ui.main.contract;

import com.uplan.miyao.base.mvp.IModel;
import com.uplan.miyao.base.mvp.IView;
import com.uplan.miyao.ui.main.model.entity.StatusCountResp;
import com.uplan.miyao.ui.main.model.entity.UserInfoResp;

import io.reactivex.Observable;


public interface MainContract {

    interface View extends IView {
        void dealSuccess(UserInfoResp resp);
        void dealStatusCountSuccess(StatusCountResp resp);
        void dealStatusCountFailure(int code, String msg);
    }

    interface Model extends IModel {
        Observable<UserInfoResp> getUserInfo();
        Observable<StatusCountResp> getStatusCount();
    }
}
