package com.hisense.hibeans.robot;

/**
 * Created by liudunjian on 2018/5/5.
 */
public class RobotPluginItemBean implements IRobotBeanDateTime {

    private int tag;
    private int imgRes;
    private String title;
    private long sendTime;

    public RobotPluginItemBean(long time, int tag, int imgRes, String title) {
        this.tag = tag;
        this.imgRes = imgRes;
        this.title = title;
        this.sendTime = time;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public long getTime() {
        return this.sendTime;
    }

    @Override
    public void sendTime(long time) {
        this.sendTime = time;
    }
}
