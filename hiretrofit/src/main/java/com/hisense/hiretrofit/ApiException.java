package com.hisense.hiretrofit;

/**
 * Created by liudunjian on 2018/4/26.
 */

public class ApiException {
    private int code;
    private String msg;

    public ApiException(int code) {
        this.code = code;
    }

    public ApiException(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Code = " + code + ", Msg = " + msg;
    }
}
