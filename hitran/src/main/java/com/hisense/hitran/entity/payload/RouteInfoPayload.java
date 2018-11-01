package com.hisense.hitran.entity.payload;

import java.util.ArrayList;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class RouteInfoPayload {

    private String total_mode;
    private ArrayList<RouteLine> routes;

    public String getTotal_mode() {
        return total_mode;
    }

    public void setTotal_mode(String total_mode) {
        this.total_mode = total_mode;
    }

    public ArrayList<RouteLine> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<RouteLine> routes) {
        this.routes = routes;
    }
}
