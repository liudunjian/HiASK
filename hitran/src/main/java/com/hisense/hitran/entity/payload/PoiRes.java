package com.hisense.hitran.entity.payload;

import java.util.List;

/**
 * Created by liudunjian on 2018/10/31.
 */

public class PoiRes {
    private List<NodePayload> map_mark;
    private String log_show;

    public List<NodePayload> getMap_mark() {
        return map_mark;
    }

    public void setMap_mark(List<NodePayload> map_mark) {
        this.map_mark = map_mark;
    }

    public String getLog_show() {
        return log_show;
    }

    public void setLog_show(String log_show) {
        this.log_show = log_show;
    }
}
