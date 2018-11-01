package com.hisense.hitran.http.factory;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by liudunjian on 2018/10/25.
 */

public class JsonBodyFactoryFactory implements IReqBodyFactory {

    private Gson gson = new Gson();

    @Override
    public RequestBody body(Object obj) {
        return  RequestBody.create(MediaType.parse("application/json"), gson.toJson(obj));
    }

}
