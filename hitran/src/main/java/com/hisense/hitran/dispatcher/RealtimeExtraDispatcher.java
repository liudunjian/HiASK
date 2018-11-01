package com.hisense.hitran.dispatcher;

import android.util.Log;

import com.google.gson.Gson;
import com.hisense.hitran.entity.payload.GeoRealtimePayload;
import com.hisense.hitran.lisener.ITranMessageListener;

/**
 * Created by liudunjian on 2018/10/30.
 */

public class RealtimeExtraDispatcher implements IPayloadDispatcher {
    private Gson gson;
    private ITranMessageListener messageListener;

    public RealtimeExtraDispatcher(Gson gson, ITranMessageListener messageListener) {
        this.gson = gson;
        this.messageListener = messageListener;
    }
    @Override
    public void dispatch(String content) {
        Log.d("RealtimeEaDispatcher:",content);
        final GeoRealtimePayload realtimePayload = this.gson.fromJson(content, GeoRealtimePayload.class);
        this.messageListener.onTextMessageContent(realtimePayload.getMainPage().getDescription());
    }
}
