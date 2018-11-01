package com.hisense.hitran.dispatcher;

import android.util.Log;

import com.google.gson.Gson;
import com.hisense.hitran.entity.payload.TranRouteWayPayload;
import com.hisense.hitran.lisener.ITranMessageListener;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class TranRouteDispatcher implements IPayloadDispatcher {

    private Gson gson;
    private ITranMessageListener messageListener;

    public TranRouteDispatcher(Gson gson,ITranMessageListener messageListener) {
        this.gson = gson;
        this.messageListener = messageListener;
    }

    public void setMessageListener(ITranMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public void dispatch(String content) {
        TranRouteWayPayload tranRouteWayPayload = this.gson.fromJson(content,TranRouteWayPayload.class);

        if(tranRouteWayPayload.getFlag().equals("route")) {
            //地图信息
            if(messageListener!=null)
                messageListener.onRouteWayMessageContent(tranRouteWayPayload);
        }else if(tranRouteWayPayload.getFlag().equals("no_route")){
            //进一步询问
            if(messageListener!=null)
                messageListener.onTextMessageContent(tranRouteWayPayload.getRes_info());
        }
    }
}
