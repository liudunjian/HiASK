package com.hisense.hitran.http.factory;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by liudunjian on 2018/10/25.
 */

public class FormBodyFactoryFactory implements IReqBodyFactory<Map<String,String>> {

    @Override
    public RequestBody body(Map<String, String> obj) {

        FormBody.Builder builder = new FormBody.Builder();

        for(String key:obj.keySet()) {
            builder.add(key,obj.get(key));
        }

        return builder.build();
    }
}
