package com.hisense.hibeans.bot.convert;

import com.google.gson.Gson;
import com.hisense.hibeans.bot.model.MessageContent;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by liudunjian on 2018/6/26.
 */

public class BotMsgContentConvert implements PropertyConverter<MessageContent, String> {

    private Gson gson = new Gson();

    @Override
    public MessageContent convertToEntityProperty(String databaseValue) {
        return gson.fromJson(databaseValue, MessageContent.class);
    }

    @Override
    public String convertToDatabaseValue(MessageContent entityProperty) {
        return gson.toJson(entityProperty);
    }
}
