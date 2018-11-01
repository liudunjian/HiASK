package com.hisense.hibeans.robot;

import java.util.Date;

/**
 * Created by liudunjian on 2018/5/15.
 */

public class RobotAudioMsgBean implements IRobotBeanDateTime {

    private int direction;
    private String filePath;
    private long sendTime;

    public RobotAudioMsgBean(long sendTime, int direction) {
        this.direction = direction;
        this.sendTime = sendTime;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public long getTime() {
        return sendTime;
    }

    @Override
    public void sendTime(long time) {
        this.sendTime = time;
    }
}
