package com.hisense.hiretrofit.transformer;

import java.util.Map;

import io.reactivex.ObservableSource;
import okhttp3.RequestBody;

/**
 * Created by liudunjian on 2017/12/18.
 */

public interface IResultMultiTransformer<T> {
    ObservableSource<T> transformers(Map<String, RequestBody> map) throws Exception;
}
