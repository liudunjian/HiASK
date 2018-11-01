package com.hisense.hitran.dispatcher;

import com.google.gson.Gson;
import com.hisense.hitran.entity.payload.AskExtraPayload;
import com.hisense.hitran.lisener.ITranMessageListener;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class AskPayloadDispatcher implements IPayloadDispatcher {

    private Gson gson;
    private ITranMessageListener messageListener;

    public AskPayloadDispatcher(Gson gson, ITranMessageListener messageListener) {
        this.gson = gson;
        this.messageListener = messageListener;
    }

    public void setMessageListener(ITranMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public void dispatch(String content) {
        AskExtraPayload payload = this.gson.fromJson(content, AskExtraPayload.class);
        if (messageListener != null)
            messageListener.onTextMessageContent(payload.getMsgbody());
    }
}
