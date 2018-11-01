package com.hisense.hiretrofit.interceptor;

import com.hisense.hitools.utils.EmptyUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通用Get参数拦截器
 * 这类参数是拼接到url后面?a=b&c=d
 */

public class QueryParamsInterceptor implements Interceptor {
    private Map<String,String> params;

    public QueryParamsInterceptor(Map<String,String> params) {
        this.params = params;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        HttpUrl.Builder builder = chain.request().url().newBuilder();
        this.addQueryParams(builder);
        requestBuilder.url(builder.build());
        return chain.proceed(requestBuilder.build());
    }

    private void addQueryParams(HttpUrl.Builder builder){
        if(EmptyUtils.isEmpty(this.params)) {
            return;
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue()).build();
        }
    }
}
