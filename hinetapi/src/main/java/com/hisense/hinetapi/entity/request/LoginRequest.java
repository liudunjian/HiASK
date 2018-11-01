package com.hisense.hinetapi.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudunjian on 2017/9/19.
 */

public class LoginRequest {

    @SerializedName("password")
    private String password;
    @SerializedName("username")
    private String username;
    @SerializedName("validateCode")
    private String validateCode;

    public LoginRequest(String username,String password,String validateCode) {
        this.password = password;
        this.username = username;
        this.validateCode = validateCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
