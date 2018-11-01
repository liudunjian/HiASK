package com.hisense.hiretrofit.builder;


import com.hisense.hiretrofit.interceptor.CacheInterceptor;
import com.hisense.hiretrofit.interceptor.HeaderInterceptor;
import com.hisense.hiretrofit.interceptor.QueryParamsInterceptor;
import com.hisense.hiretrofit.interceptor.UserAgentInterceptor;
import com.hisense.hitools.utils.EmptyUtils;
import com.hisense.hitools.utils.LogUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitBuilder {

    private String baseURL;                                     //服务器根地址
    //Cache
    private String cacheDirectory;                              //缓存目录
    private long onlineCacheTime = 2 * 60;                      //在线缓存时长s
    private long offlineCacheTime = 3 * 24 * 60 * 60;           //离线缓存时长s
    private long maxCacheMaxSize = 20 * 1024 * 1024;            //最大缓存大小

    //TimeOut
    private long readTimeout = 60;                              //读取超时时限s
    private long writeTimeout = 60;                             //链接超时时限s
    private long connectTimeout = 60;                           //链接超时时限s
    //Log
    private boolean enableDebug = true;                         //是否允许打印日志

    //Headers And Params
    private String userAgent;                                   //UserAgent请求头
    private Map<String, String> headers;                         //公共请求头
    private Map<String, String> queryParams;                     //公共请求参数?a=b&c=d

    public RetrofitBuilder() {

    }

    public RetrofitBuilder baseURL(String baseURL) {
        this.baseURL = baseURL;
        return this;
    }

    public RetrofitBuilder cacheDirectory(String cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
        return this;
    }

    public RetrofitBuilder onlineCacheTime(long onlineCacheTime) {
        this.onlineCacheTime = onlineCacheTime;
        return this;
    }

    public RetrofitBuilder offlineCacheTime(long offlineCacheTime) {
        this.offlineCacheTime = offlineCacheTime;
        return this;
    }

    public RetrofitBuilder maxCacheMaxSize(long maxCacheMaxSize) {
        this.maxCacheMaxSize = maxCacheMaxSize;
        return this;
    }

    public RetrofitBuilder readTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public RetrofitBuilder writeTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public RetrofitBuilder connectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RetrofitBuilder enableDebug(boolean enableDebug) {
        this.enableDebug = enableDebug;
        return this;
    }

    public RetrofitBuilder userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public RetrofitBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RetrofitBuilder queryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public OkHttpClient httpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(this.readTimeout, TimeUnit.SECONDS);
        builder.writeTimeout(this.writeTimeout, TimeUnit.SECONDS);
        builder.connectTimeout(this.connectTimeout, TimeUnit.SECONDS);
        if (EmptyUtils.isNotEmpty(this.userAgent)) {
            builder.addInterceptor(new UserAgentInterceptor(this.userAgent));
        }
        if (EmptyUtils.isNotEmpty(this.headers)) {
            builder.addInterceptor(new HeaderInterceptor(this.headers));
        }
        if (EmptyUtils.isNotEmpty(this.queryParams)) {
            builder.addInterceptor(new QueryParamsInterceptor(this.queryParams));
        }

        if (this.enableDebug) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.d(message);
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        if (EmptyUtils.isNotEmpty(this.cacheDirectory)) {
            builder.cache(new Cache(new File(this.cacheDirectory), this.maxCacheMaxSize));
            CacheInterceptor interceptor = new CacheInterceptor(this.onlineCacheTime, this.offlineCacheTime);
            builder.addInterceptor(interceptor);
            builder.addNetworkInterceptor(interceptor);
        }
        return builder.build();
    }

    private Retrofit retrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(this.baseURL)
                .client(this.httpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }

    public <T> T builder(Class<T> service) {
        return this.retrofit().create(service);
    }
}
