package com.hisense.hibot.http.factory;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by liudunjian on 2018/4/12.
 */

public class BotRequestFactory {

    public static Request createBotRequest(String url) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        return builder.build();
    }

    public static Request createBotRequest(String url, RequestBody body) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.post(body);
        return builder.build();
    }

}
