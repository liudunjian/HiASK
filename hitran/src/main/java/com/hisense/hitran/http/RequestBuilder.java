package com.hisense.hitran.http;

import com.google.gson.Gson;
import com.hisense.hitran.http.factory.IReqBodyFactory;
import com.hisense.hitran.http.factory.RequestBodyProducer;
import com.hisense.hitran.http.factory.RequestBodyType;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;

/**
 * Created by liudunjian on 2018/10/24.
 */

public class RequestBuilder {

    private Map<String, String> headers = new HashMap<>();
    private String url = HttpConfig.HTTP_BASE_URL;

    private Gson gson = new Gson();

    public RequestBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * if @method headers calls, must call this after that
     *
     * @param key
     * @param value
     * @return
     */
    public RequestBuilder header(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public RequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public Request formRequest(Map<String, String> params) {

        IReqBodyFactory factory = RequestBodyProducer.factory(RequestBodyType.REQUEST_BODY_TYPE_FORM);
        return new Request.Builder()
                .url(this.url)
                .headers(Headers.of(this.headers))
                .post(factory.body(params))
                .build();
    }

    public <T> Request jsonRequest(T params) {
        IReqBodyFactory factory = RequestBodyProducer.factory(RequestBodyType.REQUEST_BODY_TYPE_JSON);
        return new Request.Builder()
                .url(this.url)
                .headers(Headers.of(this.headers))
                .post(factory.body(params))
                .build();
    }

}
