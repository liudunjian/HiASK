package com.hisense.hinetapi.entity.request;

/**
 * Created by liudunjian on 2017/9/18.
 */

public class SendVerifyCodeRequest {

    private String region;
    private String accountName;

    public SendVerifyCodeRequest(String region, String phone) {
        this.region = region;
        this.accountName = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
