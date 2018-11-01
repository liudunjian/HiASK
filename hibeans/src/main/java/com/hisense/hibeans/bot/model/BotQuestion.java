package com.hisense.hibeans.bot.model;

/**
 * Created by liudunjian on 2018/4/16.
 */

public class BotQuestion {

    public static final String QuestionTypeText = "TEXT";
    public static final String QuestionTypeImage = "IMAGE";
    public static final String QuestionTypeAudio = "AUDIO";
    public static final String ChannelAPP = "APP";

    private String questionType;
    private String channel;
    private String question;
    private String userId;

    public static String getQuestionTypeText() {
        return QuestionTypeText;
    }

    public static String getQuestionTypeImage() {
        return QuestionTypeImage;
    }

    public static String getQuestionTypeAudio() {
        return QuestionTypeAudio;
    }

    public static String getChannelAPP() {
        return ChannelAPP;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
