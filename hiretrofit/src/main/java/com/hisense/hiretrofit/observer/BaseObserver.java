package com.hisense.hiretrofit.observer;

import com.hisense.hiretrofit.ApiException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by nick on 2017/9/5.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.onStart();
    }

    @Override
    public void onNext(@NonNull T entity) {
        this.onSuccess(entity);
        this.onFinish();
    }

    @Override
    public void onError(@NonNull Throwable e) {
        this.onFailure(new ApiException(1000, e.getClass().getName()));
        this.onFinish();
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onStart();

    protected abstract void onSuccess(T result);

    protected abstract void onFailure(ApiException e);

    protected abstract void onFinish();
}
