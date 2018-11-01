package com.hisense.hitran.deserializer;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hisense.hitran.Directive;
import com.hisense.hitran.Header;

import java.lang.reflect.Type;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class DirectiveDeserializer implements JsonDeserializer<Directive>{

    @Override
    public Directive deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if(json.isJsonNull())
            throw new JsonParseException("you can pass invalidate target to deserialize");

        JsonObject response = json.getAsJsonObject();
        JsonElement resultsEle = response.get("results");
        if(!resultsEle.isJsonObject())
            throw new JsonParseException("you can pass invalidate target to deserialize");
        JsonObject results = resultsEle.getAsJsonObject();

        Header header = deserializeHeader(results);

        JsonObject object = results.getAsJsonObject("object");

        return new Directive(header,object.toString());
    }

    private Header deserializeHeader(JsonObject jsonObject) {
        String domain = jsonObject.getAsJsonPrimitive("domain").getAsString();
        int info = jsonObject.getAsJsonPrimitive("info").getAsInt();
        String intent = jsonObject.getAsJsonPrimitive("intent").getAsString();
        return new Header(domain,intent,info);
    }
}
