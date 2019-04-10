package com.uplan.miyao.ui.discover.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uplan.miyao.R;
import com.uplan.miyao.base.mvp.BaseFragment;
import com.uplan.miyao.ui.discover.contract.DiscoverContract;
import com.uplan.miyao.ui.discover.presenter.DiscoverPresenter;


public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverContract.View {



    @Override
    protected DiscoverPresenter getPresenter() {
        return new DiscoverPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.activity_regist,null);
        return view;
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
