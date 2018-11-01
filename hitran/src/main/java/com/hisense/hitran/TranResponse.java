package com.hisense.hitran;

import com.google.gson.annotations.Expose;

/**
 * Created by liudunjian on 2018/10/25.
 */

public class TranResponse {

    private int err_no;
    private String err_msg;
    private String baiduresult;
    private String req_source;
    private String sessionid;

    @Expose(serialize = false, deserialize = false)
    private String content;

    public int getErr_no() {
        return err_no;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getBaiduresult() {
        return baiduresult;
    }

    public void setBaiduresult(String baiduresult) {
        this.baiduresult = baiduresult;
    }

    public String getReq_source() {
        return req_source;
    }

    public void setReq_source(String req_source) {
        this.req_source = req_source;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
