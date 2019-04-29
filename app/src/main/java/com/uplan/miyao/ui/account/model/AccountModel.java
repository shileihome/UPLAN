package com.uplan.miyao.ui.account.model;

import com.uplan.miyao.base.mvp.BaseModel;
import com.uplan.miyao.net.ResponseData;
import com.uplan.miyao.net.RxService;
import com.uplan.miyao.ui.account.api.AccountService;
import com.uplan.miyao.ui.account.contract.AccountContract;
import com.uplan.miyao.ui.account.model.resp.AccountResp;

import io.reactivex.Observable;


public class AccountModel extends BaseModel implements AccountContract.Model {

    @Override
    public Observable<AccountResp> getAccountInfo() {
        return RxService.createApi(AccountService.class).getAccountInfo();
    }

    @Override
    public Observable<ResponseData> logOut(String tel) {
        return RxService.createApi(AccountService.class).logOut(tel);
    }
}
