package com.hisense.hitran.dispatcher;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.hisense.hitran.Directive;
import com.hisense.hitran.Header;
import com.hisense.hitran.TranResponse;
import com.hisense.hitran.manager.PayloadDispatcherManager;
import com.hisense.hitran.manager.SessionManager;

/**
 * Created by liudunjian on 2018/10/25.
 */

public class DirectiveDispatcher {

    private Gson gson;
    private PayloadDispatcherManager payloadDispatcherManager;


    public DirectiveDispatcher(Gson gson,PayloadDispatcherManager payloadDispatcherManager) {
        this.gson = gson;
        this.payloadDispatcherManager = payloadDispatcherManager;
    }

    public void parseDirective(TranResponse response) {
        Directive directive = null;
        try {
            directive = gson.fromJson(response.getContent(), Directive.class);
            Header header = directive.getHeader();

            IPayloadDispatcher payloadDispatcher = null;

            if(header.getDomain().equals("traffic")) {
                if (header.getInfo() == 1) {
                    payloadDispatcher = payloadDispatcherManager.trafficDispatcher("askExtra");
                } else {
                    payloadDispatcher = payloadDispatcherManager.trafficDispatcher(header.getIntent());
                }
            }

            if (payloadDispatcher != null)
                payloadDispatcher.dispatch(directive.getPayload());
        }catch (JsonParseException e) {
            e.printStackTrace();
            //payloadDispatcherManager.tranMessageListener().onTextMessageContent("暂时无法回答你的问题");
            SessionManager.getInstance().setSessionId("");
            payloadDispatcherManager.tranMessageListener().onCannotResponse(response.getBaiduresult());
        }

    }

    public Gson gson() {
        return gson;
    }
}
