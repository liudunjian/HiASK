package com.hisense.hibeans.bot.model;

/**
 * Created by liudunjian on 2018/5/7.
 */

public class MessageContentImage extends MessageContent{
    String dataPath;

    public MessageContentImage(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDataPath() {
        return this.dataPath;
    }
}
