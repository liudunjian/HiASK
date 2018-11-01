package com.hisense.hiretrofit.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class UserAgentInterceptor implements Interceptor {
    private String userAgent;
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";

    public UserAgentInterceptor(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader(USER_AGENT_HEADER_NAME)
                .addHeader(USER_AGENT_HEADER_NAME, userAgent)
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}
