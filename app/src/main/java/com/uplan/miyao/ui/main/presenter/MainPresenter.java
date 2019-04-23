package com.uplan.miyao.ui.main.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.net.ErrorHandleSubscriber;
import com.uplan.miyao.ui.main.contract.MainContract;
import com.uplan.miyao.ui.main.model.MainModel;
import com.uplan.miyao.ui.main.model.resp.StatusCountResp;
import com.uplan.miyao.ui.main.model.resp.UserInfoResp;
import com.uplan.miyao.util.RxUtils;


public class MainPresenter extends BasePresenter<MainContract.View, MainContract.Model> {

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    protected MainContract.Model getModel() {
        return new MainModel();
    }


    /**
     * 用户信息
     */
    public void getUserInfo() {

        mModel.getUserInfo().compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<UserInfoResp>() {
            @Override
            public void onSuccess(UserInfoResp resp) {
                mView.dealSuccess(resp);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.dealFailure(code, msg);
            }
        });
    }

    /**
     * 获取各状态对应的数量
     */
    public void getStatusCount(){
        mModel.getStatusCount().compose(RxUtils.applySchedulers(mView)).subscribe(new ErrorHandleSubscriber<StatusCountResp>() {
            @Override
            public void onSuccess(StatusCountResp statusCountResp) {
                mView.dealStatusCountSuccess(statusCountResp);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.dealStatusCountFailure(code, msg);
            }
        });
    }

}
