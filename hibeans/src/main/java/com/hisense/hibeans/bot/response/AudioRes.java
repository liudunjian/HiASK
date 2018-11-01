package com.hisense.hibeans.bot.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/5/15.
 */

public class AudioRes {

    @SerializedName("result")
    private ArrayList<String> result;
    @SerializedName("err_no")
    private int err_no;
    @SerializedName("err_msg")
    private String err_msg;

    public ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }

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
}
