package com.uplan.miyao.ui.regist.contract;

import com.uplan.miyao.base.mvp.IModel;
import com.uplan.miyao.base.mvp.IView;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.ui.regist.model.resp.RegistResp;

import io.reactivex.Observable;

public interface RegistContract {

    interface View extends IView {
        void loading();
        void unLoad();
        void dealRegistSuccess(ResponseData data);
    }

    interface Model extends IModel {
        Observable<RegistResp> regist(String phoneNum, String pwd);
    }
}
