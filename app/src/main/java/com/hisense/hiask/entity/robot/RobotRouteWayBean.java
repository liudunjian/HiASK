package com.hisense.hiask.entity.robot;

import com.hisense.hibeans.robot.IRobotBeanDateTime;
import com.hisense.hitran.entity.payload.TranRouteWayPayload;

/**
 * Created by liudunjian on 2018/10/29.
 */

public class RobotRouteWayBean implements IRobotBeanDateTime {

    private long sendTime;
    private TranRouteWayPayload payload;

    public RobotRouteWayBean(long sendTime, TranRouteWayPayload payload) {
        this.sendTime = sendTime;
        this.payload = payload;
    }

    public TranRouteWayPayload getPayload() {
        return payload;
    }

    public void setPayload(TranRouteWayPayload payload) {
        this.payload = payload;
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
