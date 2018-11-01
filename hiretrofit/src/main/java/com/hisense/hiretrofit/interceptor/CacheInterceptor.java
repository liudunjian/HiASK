package com.hisense.hiretrofit.interceptor;


import com.hisense.hitools.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器
 */

public class CacheInterceptor implements Interceptor {

    private long onlineCacheTime;                       //在线缓存时长s
    private long offlineCacheTime;                      //在线缓存时长s

    public CacheInterceptor(long onlineCacheTime, long offlineCacheTime) {
        this.onlineCacheTime = onlineCacheTime;
        this.offlineCacheTime = offlineCacheTime;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isNetworkAvailable()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response response = chain.proceed(request);
        if (NetworkUtils.isNetworkAvailable()) {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public,max-age=" + 5)
                    .build();
        } else {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public,only-if-cached,max-stale=" + this.offlineCacheTime)
                    .build();
        }
        return response;
    }

}
