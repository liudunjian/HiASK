package com.hisense.hiask.map.drawer;

import com.hisense.hitran.entity.payload.PoiPayload;
import com.hisense.hitran.entity.payload.TranRouteWayPayload;

/**
 * Created by liudunjian on 2018/10/30.
 */

public interface IMapDraw {
    void drawRouteWay(TranRouteWayPayload payload);
    void drawGeoPoi(PoiPayload payload);
}
