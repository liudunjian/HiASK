package com.hisense.hiretrofit.transformer;

import java.util.Map;
import java.util.Set;

import io.reactivex.ObservableSource;
import okhttp3.RequestBody;

/**
 * Created by liudunjian on 2017/12/18.
 */

public interface IMultiBodyTransformer<T> {
    ObservableSource<Map<String, RequestBody>> transformers(Set<T> object) throws Exception;
}
