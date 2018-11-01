package com.hisense.hibeans.question;

import com.google.gson.Gson;

/**
 * Created by liudunjian on 2018/4/27.
 */

public class QuestSvcBean {

    private String answer;
    private String question;
    private String id;
    private int is_collected = 0; //1:collected

    private QuestSvcType type = QuestSvcType.QUEST_SVC_ITEM;

    public QuestSvcBean(QuestSvcType type) {
        this.type = type;
    }

    public QuestSvcBean() {

    }

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }

    public QuestSvcType getType() {
        return type;
    }

    public void setType(QuestSvcType type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public enum QuestSvcType {
        QUEST_SVC_ITEM,
        QUEST_SVC_MORE
    }
}
