package com.hisense.hitran.http.factory;

import okhttp3.RequestBody;

/**
 * Created by liudunjian on 2018/10/25.
 */

public interface IReqBodyFactory<T> {
    RequestBody body(T obj);
}
