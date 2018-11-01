package com.hisense.hiask.entity.robot;

import com.hisense.hibeans.robot.IRobotBeanDateTime;
import com.hisense.hitran.entity.payload.PoiPayload;

/**
 * Created by liudunjian on 2018/10/31.
 */

public class RobotGeoPoiBean implements IRobotBeanDateTime {

    private long sendTime;
    private PoiPayload payload;

    public RobotGeoPoiBean(long sendTime, PoiPayload payload) {
        this.sendTime = sendTime;
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

    public PoiPayload getPayload() {
        return payload;
    }

    public void setPayload(PoiPayload payload) {
        this.payload = payload;
    }
}
