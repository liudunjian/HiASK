package com.hisense.hibeans.robot;

/**
 * Created by liudunjian on 2018/4/27.
 */
public class RobotTextMsgBean implements IRobotBeanDateTime {

    private int direction;
    private String content;
    private long sendTime;


    public RobotTextMsgBean(long sendTime, int direction, String content) {
        this.sendTime = sendTime;
        this.direction = direction;
        this.content = content;
    }

    @Override
    public long getTime() {
        return this.sendTime;
    }

    @Override
    public void sendTime(long time) {
        this.sendTime = time;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
