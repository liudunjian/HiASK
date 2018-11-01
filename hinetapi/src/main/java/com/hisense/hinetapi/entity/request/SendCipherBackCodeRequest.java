package com.hisense.hinetapi.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudunjian on 2018/2/28.
 */

public class SendCipherBackCodeRequest {
    @SerializedName("accountname")
    private String accountname;

    public SendCipherBackCodeRequest(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }
}
