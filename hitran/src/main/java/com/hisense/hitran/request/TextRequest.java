package com.hisense.hitran.request;

import android.text.TextUtils;

import java.util.UUID;

/**
 * Created by liudunjian on 2018/10/24.
 */

public class TextRequest implements IBaseRequest {

    private String cuid;
    private String type;
    private String req_source;
    private String text;
    private String uuid;
    private String deviceid;
    private String location;
    private String sessionid;
    private String token;

    public TextRequest(String cuid, String type, String req_source, String text, String uuid, String deviceid, String location, String sessionid,String token) {
        this.cuid = cuid;
        this.type = type;
        this.req_source = req_source;
        this.text = text;
        this.uuid = uuid;
        this.deviceid = deviceid;
        this.location = location;
        this.sessionid = sessionid;
        this.token = token;
    }

    public static class Builder {

        private String cuid = "test";
        private String type = "text";
        private String req_source = "app";
        private String text;
        private String uuid = UUID.randomUUID().toString();
        private String deviceid = "faejsfjeafjaes";
        private String location = "40.047669,116.313082";
        private String sessionid = "987656787sdw01";
        private String token = "12323214124";

        public Builder cuid(String cuid) {
            if(!TextUtils.isEmpty(cuid))
                this.cuid = cuid;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder reqSource(String req_source) {
            this.req_source = req_source;
            return  this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder deviceId(String deviceid) {
            this.deviceid = deviceid;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder sessionId(String sessionid) {
            this.sessionid = sessionid;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public TextRequest build() {
            return new TextRequest(cuid,type,req_source,text,uuid,deviceid,location,sessionid,token);
        }

    }

}
