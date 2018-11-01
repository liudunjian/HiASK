package com.hisense.hibeans.robot;

import java.util.Date;

/**
 * Created by liudunjian on 2018/5/7.
 */

public class RobotImageMsgBean implements IRobotBeanDateTime {
    private int direction;
    private String dataPath;
    private long sendTime;

    public RobotImageMsgBean(long sendTime, int direction, String dataPath) {
        this.direction = direction;
        this.dataPath = dataPath;
        this.sendTime = sendTime;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
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
