package com.hisense.hibeans.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudunjian on 2018/4/26.
 */

public class BaseEntity<T> {

    /**
     * err_msg : success
     * type : id
     * err_no : 0
     * SST : [{"result":"231081197908151487","validation":1}]
     */

    @SerializedName("err_msg")
    private String errMsg;
    @SerializedName("type")
    private String type;
    @SerializedName("err_no")
    private int errNo;

    @SerializedName("SST")
    private T SST;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getErrNo() {
        return errNo;
    }

    public void setErrNo(int errNo) {
        this.errNo = errNo;
    }

    public T getSST() {
        return SST;
    }

    public void setSST(T sST) {
        SST = sST;
    }
}
