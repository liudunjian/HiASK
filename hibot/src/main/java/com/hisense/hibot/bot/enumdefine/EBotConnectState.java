package com.hisense.hibot.bot.enumdefine;

/**
 * Created by liudunjian on 2018/4/12.
 */

public enum EBotConnectState {

    BOT_CONNECTED_STATE(1),
    BOT_DISCONNECT_STATE(2),
    BOT_CONNECTING_STATE(3),
    BOT_CONNECT_FAILED_STATE(4);

    private int index;

    EBotConnectState(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public EBotConnectState valueOf(int value) {
        switch (value) {
            case 1:
                return BOT_CONNECTED_STATE;
            case 2:
                return BOT_DISCONNECT_STATE;
            case 3:
                return BOT_CONNECTING_STATE;
            default:
                return BOT_CONNECT_FAILED_STATE;
        }
    }
}
