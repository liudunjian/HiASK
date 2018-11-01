package com.hisense.hibeans.bot.model;

/**
 * Created by liudunjian on 2018/4/27.
 */

public class BotMenuItem {

    protected String itemId;
    protected String content;
    protected int type;

    public BotMenuItem(String itemId, String content, int type) {
        this.itemId = itemId;
        this.content = content;
        this.type = type;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
