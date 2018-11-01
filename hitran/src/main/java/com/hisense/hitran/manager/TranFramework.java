package com.hisense.hitran.manager;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hisense.hitran.Directive;
import com.hisense.hitran.deserializer.DirectiveDeserializer;
import com.hisense.hitran.dispatcher.DirectiveDispatcher;
import com.hisense.hitran.DirectiveEnqueuer;
import com.hisense.hitran.entity.payload.PoiPayload;
import com.hisense.hitran.entity.payload.TranRouteWayPayload;
import com.hisense.hitran.http.HttpManager;
import com.hisense.hitran.http.RequestBuilder;
import com.hisense.hitran.lisener.IHttpRequestListener;
import com.hisense.hitran.lisener.ITranFrameworkListener;
import com.hisense.hitran.lisener.ITranMessageListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.Observable.create;

/**
 * Created by liudunjian on 2018/10/24.
 */

public class TranFramework implements ITranMessageListener {

    private static volatile TranFramework instance;

    private Gson gson;
    private DirectiveEnqueuer directiveEnqueuer;
    private DirectiveDispatcher directiveDispatcher;
    private PayloadDispatcherManager payloadDispatcherManager;
    private ITranFrameworkListener frameworkListener;

    private String location = "40.047669,116.313082";

    public static TranFramework getInstance() {
        if (instance == null) {
            synchronized (TranFramework.class) {
                if (instance == null) {
                    instance = new TranFramework();
                }
            }
        }
        return instance;
    }

    public void setHttpRequestListener(IHttpRequestListener listener) {
        if(directiveEnqueuer!=null)
            directiveEnqueuer.setHttpRequestListener(listener);
    }

    public void setTranFrameworkListener(ITranFrameworkListener frameworkListener) {
        this.frameworkListener = frameworkListener;
    }

    public void updateLocation(String location) {
        this.location = location;
    }

    public void askQuestion(final String text) {
        create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                HttpManager.getInstance().call(
                        new RequestBuilder().formRequest(TranFramework.this.request(text)), directiveEnqueuer);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    private TranFramework() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Directive.class, new DirectiveDeserializer())
                .create();

        payloadDispatcherManager = new PayloadDispatcherManager(this);
        payloadDispatcherManager.init();

        directiveDispatcher = new DirectiveDispatcher(this.gson,this.payloadDispatcherManager);

        directiveEnqueuer = new DirectiveEnqueuer(directiveDispatcher);
        directiveEnqueuer.init();
    }

    private Map<String, String> request(String text) {
        Map<String, String> req = new HashMap<>();
        req.put("cuid", "test");
        req.put("type", "text");
        req.put("req_source", "web");
        req.put("text", text);
        req.put("uuid", UUID.randomUUID().toString());
        req.put("deviceid", "test");
        req.put("location", location);
        req.put("sessionid", SessionManager.getInstance().getSessionId());
        req.put("token", "123456");
        return req;
    }

    /***************message listener methods*************/

    @Override
    public void onTextMessageContent(String text) {
        if(TextUtils.isEmpty(text))
            return;
        if(frameworkListener!=null)
            frameworkListener.onTextMessageContent(text);
    }

    @Override
    public void onRouteWayMessageContent(TranRouteWayPayload payload) {
        if(frameworkListener!=null)
            frameworkListener.onRouteWayMessageContent(payload);
    }

    @Override
    public void onGeographicPoiContent(PoiPayload payload) {
        if(frameworkListener!=null)
            frameworkListener.onGeographicPoiContent(payload);
    }

    @Override
    public void onCannotResponse(String request) {
        if(frameworkListener!=null)
            frameworkListener.onTranCannotResponse(request);
    }

}
