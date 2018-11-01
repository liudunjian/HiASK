package com.hisense.hiretrofit.transformer;

import com.hisense.hiretrofit.factory.RequestBodyFactory;
import com.hisense.hiretrofit.observer.BaseObserver;

import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by nick on 2017/9/5.
 */

public class RetrofitTransformer {

    public static <T> ObservableTransformer<T, T> mainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> void requestTransformer(T object, BaseObserver<R> observer, final IBodyTransformer<T> tbody, final IResultTransformer<R> trelult) {
        Observable.fromArray(object)
                .flatMap(new Function<T, ObservableSource<RequestBody>>() {
                    @Override
                    public ObservableSource<RequestBody> apply(@NonNull T object) throws Exception {
                        if (null != tbody) {
                            return tbody.transformer(object);
                        } else {
                            return Observable.fromArray(RequestBodyFactory.convertToJsonRequestBody(object));
                        }
                    }
                })
                .flatMap(new Function<RequestBody, ObservableSource<R>>() {
                    @Override
                    public ObservableSource<R> apply(@NonNull RequestBody requestBody) throws Exception {
                        return trelult.transformer(requestBody);
                    }
                })
                .compose(RetrofitTransformer.<R>mainThread())
                .subscribe(observer);
    }
}
