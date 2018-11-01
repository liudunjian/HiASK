package com.hisense.hibeans.bot.model;

import com.hisense.hibeans.bot.convert.BotMsgContentConvert;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by liudunjian on 2018/4/27.
 */

@Entity
public class BotMessage {

    @Transient
    public static final int DirectionSend = 0;
    public static final int DirectionRecv = 1;

    public static final int ContentTypeNull = 0;
    public static final int ContentTypeText = 1;
    public static final int ContentTypeMenu = 2;
    public static final int ContentTypeImage = 3;
    public static final int ContentTypeRichText = 4;
    public static final int ContentTypeAudio = 5;
    public static final int ContentTypeVideo = 6;

    @Id
    protected long id;
    protected long sendTime;
    protected int direction;
    protected int contentType;

    protected String content;

    public BotMessage(long sendTime, int direction, int contentType,
                      String content) {
        this.sendTime = sendTime;
        this.direction = direction;
        this.contentType = contentType;
        this.content = content;
    }




    @Generated(hash = 902940042)
    public BotMessage(long id, long sendTime, int direction, int contentType,
            String content) {
        this.id = id;
        this.sendTime = sendTime;
        this.direction = direction;
        this.contentType = contentType;
        this.content = content;
    }




    @Generated(hash = 1626855543)
    public BotMessage() {
    }




    public static String contentTypeToString(int type) {
        switch (type) {
            case 1:
                return "TEXT";
            case 2:
                return "MENU";
            case 3:
                return "IMAGE";
            case 4:
                return "RICH_TEXT";
            case 5:
                return "AUDIO";
            case 6:
                return "VIDEO";
            default:
                return null;
        }
    }

    public static int contentTypeFromString(String type) {
        if (type == null) {
            return 0;
        } else {
            byte var2 = -1;
            switch (type.hashCode()) {
                case 2362719:
                    if (type.equals("MENU")) {
                        var2 = 1;
                    }
                    break;
                case 2571565:
                    if (type.equals("TEXT")) {
                        var2 = 0;
                    }
                    break;
                case 62628790:
                    if (type.equals("AUDIO")) {
                        var2 = 4;
                    }
                    break;
                case 69775675:
                    if (type.equals("IMAGE")) {
                        var2 = 2;
                    }
                    break;
                case 81665115:
                    if (type.equals("VIDEO")) {
                        var2 = 5;
                    }
                    break;
                case 1973534384:
                    if (type.equals("RICH_TEXT")) {
                        var2 = 3;
                    }
            }

            switch (var2) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 3;
                case 3:
                    return 4;
                case 4:
                    return 5;
                case 5:
                    return 6;
                default:
                    return 0;
            }
        }
    }




    public long getId() {
        return this.id;
    }




    public void setId(long id) {
        this.id = id;
    }




    public long getSendTime() {
        return this.sendTime;
    }




    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }




    public int getDirection() {
        return this.direction;
    }




    public void setDirection(int direction) {
        this.direction = direction;
    }




    public int getContentType() {
        return this.contentType;
    }




    public void setContentType(int contentType) {
        this.contentType = contentType;
    }




    public String getContent() {
        return this.content;
    }




    public void setContent(String content) {
        this.content = content;
    }


}
