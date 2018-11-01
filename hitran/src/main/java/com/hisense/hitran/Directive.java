package com.hisense.hitran;

import com.google.gson.annotations.Expose;

/**
 * Created by liudunjian on 2018/10/25.
 */

public class Directive  {

    private Header header;

    @Expose(serialize = false, deserialize = false)
    private String payload;

    public Directive(Header header, String payload) {
        this.header = header;
        this.payload = payload;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
