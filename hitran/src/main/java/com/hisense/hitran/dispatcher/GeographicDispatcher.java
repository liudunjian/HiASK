package com.hisense.hitran.dispatcher;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.hisense.hitran.entity.payload.GeoRoadPayload;
import com.hisense.hitran.entity.payload.GeographicPayload;
import com.hisense.hitran.entity.payload.PoiPayload;
import com.hisense.hitran.lisener.ITranMessageListener;

/**
 * Created by liudunjian on 2018/10/30.
 */

public class GeographicDispatcher implements IPayloadDispatcher {

    private Gson gson;
    private ITranMessageListener messageListener;

    public GeographicDispatcher(Gson gson, ITranMessageListener messageListener) {
        this.gson = gson;
        this.messageListener = messageListener;
    }

    @Override
    public void dispatch(String content) {

        GeographicPayload geographicPayload = this.gson.fromJson(content, GeographicPayload.class);

        if (geographicPayload.getFlag().equals("poi")) {
            PoiPayload poiPayload = this.gson.fromJson(geographicPayload.getPayload(),PoiPayload.class);
            if(poiPayload.getPoi_res().getMap_mark()==null||poiPayload.getPoi_res().getMap_mark().size()==0)
                this.messageListener.onTextMessageContent(poiPayload.getPoi_res().getLog_show());
            else
                this.messageListener.onGeographicPoiContent(poiPayload);

        } else if(geographicPayload.getFlag().equals("range")){
            PoiPayload poiPayload = this.gson.fromJson(geographicPayload.getPayload(),PoiPayload.class);
            this.messageListener.onGeographicPoiContent(poiPayload);
        }else if(geographicPayload.getFlag().equals("road")) {
            GeoRoadPayload geoRoadPayload = this.gson.fromJson(geographicPayload.getPayload(),GeoRoadPayload.class);
            this.messageListener.onTextMessageContent(geoRoadPayload.getRoad_res());
        }
    }
}
