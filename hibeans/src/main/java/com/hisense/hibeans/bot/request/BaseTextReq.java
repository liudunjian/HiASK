package com.hisense.hibeans.bot.request;

import com.google.gson.annotations.SerializedName;
import com.hisense.hibeans.bot.response.MessageRes;

/**
 * Created by liudunjian on 2018/7/5.
 */

public class BaseTextReq {

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
    private MessageRes SST;

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

    public MessageRes getSST() {
        return SST;
    }

    public void setSST(MessageRes sST) {
        SST = sST;
    }
}
