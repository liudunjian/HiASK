package com.hisense.hibeans.robot;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by liudunjian on 2018/4/28.
 */

public class RobotMenuRecList implements IRobotBeanDateTime {

    private ArrayList<RobotMenuRecBean> recommends;
    private long sendTime;

    public RobotMenuRecList(long sendTime, ArrayList<RobotMenuRecBean> recommends) {
        this.recommends = recommends;
        this.sendTime = sendTime;
    }

    public ArrayList<RobotMenuRecBean> getRecommends() {
        return recommends;
    }

    public void setRecommends(ArrayList<RobotMenuRecBean> recommends) {
        this.recommends = recommends;
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
