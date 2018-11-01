package com.hisense.hibot.bot.listener;

import com.hisense.hibeans.bot.model.BotMenuItem;
import com.hisense.hibeans.bot.model.BotMessage;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/4/27.
 */

public interface IBotMessageListener {
    void onReceivedSuggestion(ArrayList<BotMenuItem> var1);

    void onAppendMessage(BotMessage var1);
}
