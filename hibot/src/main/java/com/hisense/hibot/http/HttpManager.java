package com.hisense.hibot.http;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by liudunjian on 2018/4/11.
 */

public final class HttpManager {

    private static final String TAG = HttpManager.class.getSimpleName();

    private static HttpManager httpManager = null;

    private OkHttpClient okHttpClient;

    public static HttpManager getInstance() {
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null)
                    httpManager = new HttpManager();
            }
        }
        return httpManager;
    }

    private HttpManager() {
        this.okHttpClient = okHttpClient();
    }

    public void call(Request request, Callback callback) {
        okHttpClient.newCall(request).enqueue(callback);
    }

    public Response call(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public WebSocket webSocket(Request request, WebSocketListener listener) {
        return okHttpClient.newWebSocket(request,listener);
    }

    /**
     * initialize the OkHttpClient
     * <p>
     * the direct way to get the OkHttpClient
     * {@link okhttp3.OkHttpClient#newCall(Request)}
     * </p>
     * @return
     */

    private OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_WRITE_TIME_OUT, TimeUnit.SECONDS);
        if (HttpConfig.HTTP_ENABLE_DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d(TAG, message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

}
