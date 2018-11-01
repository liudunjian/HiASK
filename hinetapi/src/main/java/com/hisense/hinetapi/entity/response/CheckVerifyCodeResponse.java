package com.hisense.hinetapi.entity.response;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class CheckVerifyCodeResponse {
    private String verification_token;

    public void setVerification_token(String verification_token) {
        this.verification_token = verification_token;
    }

    public String getVerification_token() {
        return verification_token;
    }
}
