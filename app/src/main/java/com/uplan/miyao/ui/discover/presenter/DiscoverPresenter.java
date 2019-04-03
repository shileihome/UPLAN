package com.uplan.miyao.ui.discover.presenter;

import com.uplan.miyao.base.mvp.BasePresenter;
import com.uplan.miyao.ui.discover.contract.DiscoverContract;
import com.uplan.miyao.ui.discover.model.DiscoverModel;


public class DiscoverPresenter extends BasePresenter<DiscoverContract.View, DiscoverContract.Model> {

    public DiscoverPresenter(DiscoverContract.View view) {
        super(view);
    }

    @Override
    protected DiscoverContract.Model getModel() {
        return new DiscoverModel();
    }

}
