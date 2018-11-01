package com.hisense.hibeans.bot.response;

/**
 * Created by liudunjian on 2018/4/27.
 */

public class BotAnswerItem {
    private String id;
    private String content;
    private String type;

    public BotAnswerItem() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
