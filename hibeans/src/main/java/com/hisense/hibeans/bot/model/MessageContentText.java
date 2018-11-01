package com.hisense.hibeans.bot.model;

/**
 * Created by liudunjian on 2018/4/27.
 */

public class MessageContentText extends MessageContent {
    private String text;

    public MessageContentText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
