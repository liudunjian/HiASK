package com.hisense.hiretrofit.transformer;

import io.reactivex.ObservableSource;
import okhttp3.RequestBody;

/**
 * Created by nick on 2017/10/25.
 */

public interface IResultTransformer<T> {
    ObservableSource<T> transformer(RequestBody requestBody) throws Exception;
}
