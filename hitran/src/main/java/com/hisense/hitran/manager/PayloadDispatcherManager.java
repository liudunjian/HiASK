package com.hisense.hitran.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hisense.hitran.deserializer.GeographicDeserializer;
import com.hisense.hitran.dispatcher.AskPayloadDispatcher;
import com.hisense.hitran.dispatcher.GeographicDispatcher;
import com.hisense.hitran.dispatcher.IPayloadDispatcher;
import com.hisense.hitran.dispatcher.RealtimeExtraDispatcher;
import com.hisense.hitran.dispatcher.TranRouteDispatcher;
import com.hisense.hitran.entity.payload.GeographicPayload;
import com.hisense.hitran.lisener.ITranMessageListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class PayloadDispatcherManager {

    private Gson gson;
    private Map<String, IPayloadDispatcher> trafficDispatcherMap;
    private ITranMessageListener tranMessageListener;

    public PayloadDispatcherManager (ITranMessageListener messageListener) {
        this.tranMessageListener = messageListener;
    }

    public void init() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(GeographicPayload.class,new GeographicDeserializer())
                .create();
        initTrafficDispatcherMap();
        initOtherDomainDispatcherMap();
    }

    public IPayloadDispatcher trafficDispatcher(String key) {
        return trafficDispatcherMap.get(key);
    }

    public ITranMessageListener tranMessageListener() {
        return tranMessageListener;
    }

    private void initTrafficDispatcherMap() {

        if (trafficDispatcherMap == null) {
            trafficDispatcherMap = new HashMap<>();
            trafficDispatcherMap.put("routeway", new TranRouteDispatcher(gson,tranMessageListener));
            trafficDispatcherMap.put("askExtra", new AskPayloadDispatcher(gson,tranMessageListener));
            trafficDispatcherMap.put("realtimetra",new RealtimeExtraDispatcher(gson,tranMessageListener));
            trafficDispatcherMap.put("geographic",new GeographicDispatcher(gson,tranMessageListener));
        }

    }

    private void initOtherDomainDispatcherMap() {

    }
}
