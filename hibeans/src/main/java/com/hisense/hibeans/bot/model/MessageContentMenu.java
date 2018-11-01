package com.hisense.hibeans.bot.model;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/4/28.
 */

public class MessageContentMenu extends MessageContent{
    public static final int TypeMenu = 0;
    public static final int TypeRecommend = 1;
    protected int menuType;
    protected ArrayList<BotMenuItem> botMenuItems;

    public MessageContentMenu(int menuType, ArrayList<BotMenuItem> botMenuItems) {
        this.menuType = menuType;
        this.botMenuItems = botMenuItems;
    }

    public int getMenuType() {
        return this.menuType;
    }

    public ArrayList<BotMenuItem> getBotMenuItems() {
        return this.botMenuItems;
    }
}
