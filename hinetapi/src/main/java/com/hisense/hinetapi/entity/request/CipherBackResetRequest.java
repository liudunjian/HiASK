package com.hisense.hinetapi.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudunjian on 2018/2/28.
 */

public class CipherBackResetRequest {

    @SerializedName("accountname")
    private String accountname;
    @SerializedName("userPwd")
    private String userPwd;
    @SerializedName("validatecode")
    private String validatecode;

    public CipherBackResetRequest(String accountname, String userPwd, String validatecode) {
        this.accountname = accountname;
        this.userPwd = userPwd;
        this.validatecode = validatecode;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getValidatecode() {
        return validatecode;
    }

    public void setValidatecode(String validatecode) {
        this.validatecode = validatecode;
    }
}
