package com.hisense.hitran.entity.payload;

/**
 * Created by liudunjian on 2018/10/26.
 */

public class Step {


    private String title;

    private String mode;
    private String instruction;
    private String road_name;
    private String polylines;
    private String dir_desc;
    private String distance;
    private String act_desc;

    private int station_count;
    private NodePayload get_off;
    private NodePayload get_on;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getStation_count() {
        return station_count;
    }

    public void setStation_count(int station_count) {
        this.station_count = station_count;
    }

    public NodePayload getGet_off() {
        return get_off;
    }

    public void setGet_off(NodePayload get_off) {
        this.get_off = get_off;
    }

    public NodePayload getGet_on() {
        return get_on;
    }

    public void setGet_on(NodePayload get_on) {
        this.get_on = get_on;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getRoad_name() {
        return road_name;
    }

    public void setRoad_name(String road_name) {
        this.road_name = road_name;
    }

    public String getPolylines() {
        return polylines;
    }

    public void setPolylines(String polylines) {
        this.polylines = polylines;
    }

    public String getDir_desc() {
        return dir_desc;
    }

    public void setDir_desc(String dir_desc) {
        this.dir_desc = dir_desc;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAct_desc() {
        return act_desc;
    }

    public void setAct_desc(String act_desc) {
        this.act_desc = act_desc;
    }
}
