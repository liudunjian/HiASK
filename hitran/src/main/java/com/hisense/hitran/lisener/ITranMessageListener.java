package com.hisense.hitran.lisener;

import com.hisense.hitran.entity.payload.PoiPayload;
import com.hisense.hitran.entity.payload.TranRouteWayPayload;

/**
 * Created by liudunjian on 2018/10/28.
 */

public interface ITranMessageListener {

    void onTextMessageContent(String text);

    void onRouteWayMessageContent(TranRouteWayPayload payload);

    void onGeographicPoiContent(PoiPayload payload);

    void onCannotResponse(String request);
}
