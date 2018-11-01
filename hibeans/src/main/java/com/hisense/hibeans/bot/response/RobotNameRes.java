package com.hisense.hibeans.bot.response;

/**
 * Created by liudunjian on 2018/4/12.
 */

public class RobotNameRes {
    private String robotId;
    private Integer tenantId;
    private String name;
    private boolean active;

    public RobotNameRes() {
    }

    public String getRobotId() {
        return this.robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public Integer getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
