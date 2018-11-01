package com.hisense.hinetapi.entity.request;

/**
 * Created by liudunjian on 2017/9/18.
 */

public class CheckPhoneRequest {

    private String accountName;

    private String region;

    public CheckPhoneRequest(String accountName, String region) {
        this.accountName = accountName;
        this.region = region;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
