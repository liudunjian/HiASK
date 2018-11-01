package com.hisense.hibeans.bot.request;

import com.google.gson.annotations.SerializedName;
import com.hisense.hibeans.bot.model.BotQuestion;

/**
 * Created by liudunjian on 2018/4/16.
 */

public class MessageReq {
    public static final String TypeAsk = "ASK";
    public static final String TypeAskById = "ASKBYID";
    public static final String TypeSuggestion = "SUGGESTION";
    public static final String ContentTypeMenu = "MENU";
    public static final String ContentTypeRecommend = "RECOMMEND";
    @SerializedName("type")
    private String type;
    @SerializedName("userQuestion")
    private BotQuestion question;
    @SerializedName("contentType")
    private String contentType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BotQuestion getQuestion() {
        return question;
    }

    public void setQuestion(BotQuestion question) {
        this.question = question;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
