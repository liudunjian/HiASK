package com.hisense.hiask.robot;

import com.hisense.hiask.mvpbase.IBaseView;
import com.hisense.hibeans.bot.model.BotMenuItem;
import com.hisense.hibot.bot.enumdefine.EBotConnectState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudunjian on 2018/4/25.
 */

public interface IRobot extends IBaseView {
    void onDataPreparedStarted();

    void onDataPreparedSuccess(List<Object> data);

    void onDataPreparedFailed();

    void notifyBotConnectState(EBotConnectState state);

    void onBotSuggestionUpdate(ArrayList<BotMenuItem> items);

    void onBotMessageUpdate(Object item);

    void onRecordRmsChanged(int rms);

    void onCacheDataPrepared(List<Object> data);
}
