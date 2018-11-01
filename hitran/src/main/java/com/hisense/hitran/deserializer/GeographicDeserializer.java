package com.hisense.hitran.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hisense.hitran.entity.payload.GeographicPayload;

import java.lang.reflect.Type;

/**
 * Created by liudunjian on 2018/10/30.
 */

public class GeographicDeserializer implements JsonDeserializer<GeographicPayload> {
    @Override
    public GeographicPayload deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String flag = jsonObject.getAsJsonPrimitive("flag").getAsString();
        return new GeographicPayload(flag,jsonObject.toString());
    }
}
