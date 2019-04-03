package com.uplan.miyao.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Author: Created by fangmingdong on 2018/4/12-下午6:17
 * Description:
 */
public abstract class ErrorHandleObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }


    @Override
    public void onError(Throwable e) {
        Timber.e(e);
    }

    @Override
    public void onComplete() {

    }
}
