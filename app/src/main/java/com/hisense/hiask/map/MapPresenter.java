package com.hisense.hiask.map;

import android.content.Context;

import com.hisense.hiask.mvpbase.BasePresenter;

/**
 * Created by liudunjian on 2018/10/29.
 */

public class MapPresenter extends BasePresenter<IMapView> {

    public MapPresenter(Context context, IMapView iMapView) {
        super(context, iMapView);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onResume() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onDestroy() {

    }

//    public void parseIntent(Intent intent) {
//
//        final Bundle bundle = intent.getBundleExtra("START_NEW_ACTIVITY_BUNDLE_KEY");
//        mapActivityType = bundle.getInt(MapActivity.MAP_ACTIVITY_TYPE);
//        if(mapActivityType == 0) {
//            mapActivityExtra = bundle.getString(MapActivity.MAP_ACTIVITY_EXTRA_INFO);
//            final TranRouteWayPayload payload = new Gson().fromJson(mapActivityExtra,TranRouteWayPayload.class);
//            if(isAttached())
//                attachedView.get().onRouteWayPayloadParsed(payload);
//        }
//
//    }
}
