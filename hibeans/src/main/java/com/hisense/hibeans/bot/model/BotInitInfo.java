package com.hisense.hibeans.bot.model;

/**
 * Created by liudunjian on 2018/4/11.
 */

public class BotInitInfo {

    public String accessKey;
    public String userId;
    public String phone;
    public String userName;

    public BotInitInfo() {
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
