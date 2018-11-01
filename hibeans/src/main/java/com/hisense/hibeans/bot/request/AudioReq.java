package com.hisense.hibeans.bot.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudunjian on 2018/5/15.
 */

public class AudioReq {

    @SerializedName("uuid")
    private String uuid;
    @SerializedName("multirounds")
    private String multirounds;
    @SerializedName("location")
    private String location;
    @SerializedName("deviceid")
    private String deviceid;
    @SerializedName("speech")
    private String speech;

    public AudioReq(String uuid, String multirounds, String location, String deviceid) {
        this.uuid = uuid;
        this.multirounds = multirounds;
        this.location = location;
        this.deviceid = deviceid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMultirounds() {
        return multirounds;
    }

    public void setMultirounds(String multirounds) {
        this.multirounds = multirounds;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }
}
