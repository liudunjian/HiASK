package com.hisense.hiretrofit.factory;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by nick on 2017/8/28.
 */

public class RequestBodyFactory {

    public static RequestBody convertToJsonRequestBody(Object param) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
    }

    public static MultipartBody.Part convertToFileMultipartBody(String path) {
        File file = new File(path);
        return MultipartBody.Part.createFormData("file", file.getName(), RequestBody.
                create(okhttp3.MediaType.parse("multipart/form-data; charset=utf-8"), file));
    }

    public static RequestBody convertToFileRequestBody(String path) {
        File file = new File(path);
        return RequestBody.
                create(okhttp3.MediaType.parse("multipart/form-data; charset=utf-8"), file);
    }

    public static Map<String,RequestBody> convertToMultiFileRequestBody(Set<String> paths) {
        Map<String,RequestBody> map = new HashMap<>();
        for(String path:paths) {
            File file = new File(path);
            map.put("file\";filename = \""+file.getName(),convertToFileRequestBody(path));
        }
        return map;
    }
}
