package com.hisense.hitran.entity.payload;

import com.google.gson.annotations.Expose;

/**
 * Created by liudunjian on 2018/10/30.
 */

public class GeographicPayload {
    private String flag;

    @Expose(serialize = false, deserialize = false)
    private String payload;

    public GeographicPayload(String flag, String payload) {
        this.flag = flag;
        this.payload = payload;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
