package com.hisense.hinetapi.entity.request;

/**
 * Created by liudunjian on 2017/9/18.
 */

public class RegisterRequest {

    private String accountName;
    private String nickName;
    private String userPwd;
    private String validateCode;

    public RegisterRequest(String accountName, String nickName, String userPwd, String validateCode) {
        this.accountName = accountName;
        this.nickName = nickName;
        this.userPwd = userPwd;
        this.validateCode = validateCode;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public String getValidateCode() {
        return validateCode;
    }
}
