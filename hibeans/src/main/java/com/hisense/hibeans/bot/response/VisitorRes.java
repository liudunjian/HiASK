package com.hisense.hibeans.bot.response;

/**
 * Created by liudunjian on 2018/4/12.
 */

public class VisitorRes {

    private Integer tenantId;
    private String visitorId;

    public VisitorRes() {
    }

    public Integer getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }
}
