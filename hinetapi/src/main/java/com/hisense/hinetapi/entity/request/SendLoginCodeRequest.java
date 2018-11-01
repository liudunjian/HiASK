package com.hisense.hinetapi.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudunjian on 2018/2/28.
 */

public class SendLoginCodeRequest {
    @SerializedName("username")
    private String username;

    public SendLoginCodeRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
