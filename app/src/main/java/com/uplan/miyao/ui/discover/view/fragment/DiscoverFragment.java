package com.uplan.miyao.ui.discover.view.fragment;

import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.discover.contract.DiscoverContract;
import com.uplan.miyao.ui.discover.presenter.DiscoverPresenter;


public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverContract.View {



    @Override
    protected DiscoverPresenter getPresenter() {
        return new DiscoverPresenter(this);
    }

/*
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void killMyself() {
        finish();
    }
*/

    @Override
    public void dealFailure(int code, String message) {

    }
}
