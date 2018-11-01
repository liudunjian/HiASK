package com.hisense.hibot.bot.listener;

import com.hisense.hibot.bot.enumdefine.EBotConnectState;

/**
 * Created by liudunjian on 2018/4/16.
 */

public interface IBotConnectionListener {
    void onBotConnectionStateChanged(EBotConnectState state);
}
