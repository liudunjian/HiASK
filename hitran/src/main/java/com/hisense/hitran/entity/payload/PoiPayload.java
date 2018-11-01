package com.hisense.hitran.entity.payload;

import java.util.List;

/**
 * Created by liudunjian on 2018/10/30.
 */

public class PoiPayload {

    private List<PoiAttribute> attribute;
    private PoiRes poi_res;

    public List<PoiAttribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<PoiAttribute> attribute) {
        this.attribute = attribute;
    }

    public PoiRes getPoi_res() {
        return poi_res;
    }

    public void setPoi_res(PoiRes poi_res) {
        this.poi_res = poi_res;
    }
}
