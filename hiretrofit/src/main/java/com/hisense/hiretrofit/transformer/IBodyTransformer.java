package com.hisense.hiretrofit.transformer;

import io.reactivex.ObservableSource;
import okhttp3.RequestBody;

/**
 * Created by nick on 2017/10/25.
 */

public interface IBodyTransformer<T> {
    ObservableSource<RequestBody> transformer(T object) throws Exception;
}
