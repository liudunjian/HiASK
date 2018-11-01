package com.hisense.hitran.entity.payload;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class TranRouteWayPayload {

    private String flag;

    private String res_info;

    private NodePayload origin;
    private NodePayload destination;

    private ArrayList<RouteInfoPayload> route_info;


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRes_info() {
        return res_info;
    }

    public void setRes_info(String res_info) {
        this.res_info = res_info;
    }

    public NodePayload getOrigin() {
        return origin;
    }

    public void setOrigin(NodePayload origin) {
        this.origin = origin;
    }

    public NodePayload getDestination() {
        return destination;
    }

    public void setDestination(NodePayload destination) {
        this.destination = destination;
    }

    public ArrayList<RouteInfoPayload> getRoute_info() {
        return route_info;
    }

    public void setRoute_info(ArrayList<RouteInfoPayload> route_info) {
        this.route_info = route_info;
    }
}
