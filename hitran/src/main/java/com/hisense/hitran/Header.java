package com.hisense.hitran;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class Header implements IHeader {
    private String domain;
    private String intent;
    private int info;

    public Header(String domain, String intent, int info) {
        this.domain = domain;
        this.intent = intent;
        this.info = info;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }
}
