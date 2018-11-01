package com.hisense.hibeans.bot.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudunjian on 2018/7/5.
 */

public class TextReq {
    @SerializedName("question")
    private String question;

    private String channel = "API";

    private String questionType = "TEXT";

    public TextReq(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
