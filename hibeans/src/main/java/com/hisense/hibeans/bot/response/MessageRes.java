package com.hisense.hibeans.bot.response;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/4/27.
 */

public class MessageRes {
    public static final String ModuleNone = "NONE";
    public static final String ModuleKB = "KB";
    public static final String ModuleKG = "KG";
    public static final String ModuleDialog = "DIALOG";
    public static final String ModuleChat = "CHAT";
    public static final String AnswerTypeDefault = "DEFAULT";
    public static final String AnswerTypeSuggestion = "SUGGESTION";
    public static final String AnswerTypeBestMatch = "BESTMATCH";
    public static final String AnswerTypeRecommend = "RECOMMEND";
    public static final String AnswerTypeMenu = "MENU";
    private String module;
    private String answerType;
    private String answerContentType;
    private String extraAnswerInfo;
    private String answer;
    private Double score;
    private ArrayList<BotAnswerItem> items;

    public MessageRes() {
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAnswerType() {
        return this.answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getAnswerContentType() {
        return this.answerContentType;
    }

    public void setAnswerContentType(String answerContentType) {
        this.answerContentType = answerContentType;
    }

    public String getExtraAnswerInfo() {
        return this.extraAnswerInfo;
    }

    public void setExtraAnswerInfo(String extraAnswerInfo) {
        this.extraAnswerInfo = extraAnswerInfo;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getScore() {
        return this.score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public ArrayList<BotAnswerItem> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<BotAnswerItem> items) {
        this.items = items;
    }
}
