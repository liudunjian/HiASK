package com.hisense.hiask.map.drawer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.hisense.hiask.hiask.R;
import com.hisense.hitools.utils.EmptyUtils;
import com.hisense.hitran.entity.payload.NodePayload;
import com.hisense.hitran.entity.payload.PoiAttribute;
import com.hisense.hitran.entity.payload.PoiPayload;
import com.hisense.hitran.entity.payload.Step;
import com.hisense.hitran.entity.payload.TranRouteWayPayload;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by liudunjian on 2018/10/30.
 */

public class MapDrawAPI implements IMapDraw {

    private Context context;
    private WeakReference<AMap> aMapWeakReference;

    public MapDrawAPI(Context context, AMap aMap) {
        this.context = context.getApplicationContext();
        aMapWeakReference = new WeakReference<AMap>(aMap);
    }

    public boolean isAttached() {
        return aMapWeakReference != null && aMapWeakReference.get() != null;
    }

    public void release() {
        aMapWeakReference.clear();
        aMapWeakReference = null;
    }

    @Override
    public void drawRouteWay(TranRouteWayPayload payload) {
        try {
            ArrayList<Step> steps = payload.getRoute_info().get(0).getRoutes().get(0).getSteps();
            for (Step step : steps) {
                polyStepOnMap(step);
            }
            zoomToSpan(markOriginPosition(payload.getOrigin()), markDestinationPosition(payload.getDestination()));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawGeoPoi(PoiPayload payload) {
        markPoiPosition(payload);
    }

    /**
     * draw polyline on map
     *
     * @param step
     */

    private void polyStepOnMap(Step step) {

        String polyString = step.getPolylines().trim();
        String[] polylines = polyString.split(";");
        List<LatLng> latLngs = new ArrayList<>();

        for (String str : polylines) {
            latLngs.add(stringToLatLng(str));
        }

        if (TextUtils.isEmpty(step.getMode()) || step.getMode().equals("walking")) {
            if (isAttached())
                aMapWeakReference.get().addPolyline(new PolylineOptions().
                        addAll(latLngs).width(10).color(Color.BLUE));
        } else if (step.getMode().equals("bus") || step.getMode().equals("subway")) {
            markBusGetOnPosition(step.getGet_on(), "乘坐:" + step.getTitle());
            markBusGetOffPosition(step.getGet_off(), "在该站下车");
            if (isAttached()) {
                aMapWeakReference.get().addPolyline(new PolylineOptions().
                        addAll(latLngs).width(10).color(Color.YELLOW));
            }
        } else if (step.getMode().equals("bicycling")) {
            if (isAttached())
                aMapWeakReference.get().addPolyline(new PolylineOptions().
                        addAll(latLngs).width(10).color(Color.BLUE));
        } else if (step.getMode().equals("driving")) {
            if (isAttached())
                aMapWeakReference.get().addPolyline(new PolylineOptions().
                        addAll(latLngs).width(10).color(Color.BLUE));
        } else {
            if (isAttached())
                aMapWeakReference.get().addPolyline(new PolylineOptions().
                        addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
        }

    }

    private void markBusGetOnPosition(NodePayload payload, String des) {
        final String location = payload.getLocation();
        final LatLng latLng = stringToLatLng(location);

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(payload.getTitle()).snippet(des);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(this.context.getResources(), R.drawable.ic_bus_station)));

        if (isAttached())
            aMapWeakReference.get().addMarker(markerOption);
    }

    private void markBusGetOffPosition(NodePayload payload, String des) {
        final String location = payload.getLocation();
        final LatLng latLng = stringToLatLng(location);

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(payload.getTitle()).snippet(des);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(this.context.getResources(), R.drawable.ic_bus_station)));

        if (isAttached())
            aMapWeakReference.get().addMarker(markerOption);
    }


    private void markPoiPosition(PoiPayload poiPayload) {
        try {
            List<LatLng> latLngs = new ArrayList<>();
            List<PoiAttribute> poiAttributes = poiPayload.getAttribute();

            for (int i = 0; i < poiAttributes.size(); i++) {

                PoiAttribute poiAttribute = poiAttributes.get(i);

                final String location = poiAttribute.getLocation();
                final LatLng latLng = stringToLatLng(location);

                StringBuilder stringBuilder = new StringBuilder();
                if (EmptyUtils.isNotEmpty(poiAttribute.getType())) {
                    stringBuilder.append("类型：" + poiAttribute.getType() + "\n");
                }

                if (EmptyUtils.isNotEmpty(poiAttribute.getAddress())) {
                    stringBuilder.append("地址：" + poiAttribute.getAddress() + "\n");
                }

                if(EmptyUtils.isNotEmpty(poiAttribute.getTel())) {
                    stringBuilder.append("电话：" + poiAttribute.getTel() + "\n");
                }


                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(latLng);
                markerOption.title(poiAttribute.getTitle()).snippet(stringBuilder.toString());
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(this.context.getResources(), R.drawable.icon_poi)));
                latLngs.add(latLng);
                if (isAttached())
                    aMapWeakReference.get().addMarker(markerOption);

            }

            zoomToSpan(latLngs);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    /**
     * mark the origin position in map
     *
     * @param origin
     * @return
     */
    private LatLng markOriginPosition(NodePayload origin) {

        final String location = origin.getLocation();
        final LatLng latLng = stringToLatLng(location);

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(origin.getTitle()).snippet("坐标:" + origin.getLocation());
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(this.context.getResources(), R.drawable.icon_st)));

        if (isAttached())
            aMapWeakReference.get().addMarker(markerOption);
        return latLng;
    }

    /**
     * mark the destination in map
     *
     * @param destination
     * @return
     */
    private LatLng markDestinationPosition(NodePayload destination) {

        final String location = destination.getLocation();
        final LatLng latLng = stringToLatLng(location);

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(destination.getTitle()).snippet("坐标:" + destination.getLocation());

        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(this.context.getResources(), R.drawable.icon_en)));
        if (isAttached())
            aMapWeakReference.get().addMarker(markerOption);

        return latLng;
    }


    /**
     * transform String to LatLng
     */

    private LatLng stringToLatLng(String str) {
        String[] xy = str.trim().split(",");
        return new LatLng(Double.valueOf(xy[0]), Double.valueOf(xy[1]));
    }

    /**
     * 缩放移动地图，保证所有自定义marker在可视范围中。
     */
    private void zoomToSpan(LatLng st, LatLng end) {

        if (isAttached()) {
            List<LatLng> pointList = new ArrayList<>();
            pointList.add(st);
            pointList.add(end);
            LatLngBounds bounds = getLatLngBounds(pointList);
            aMapWeakReference.get().moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
        }

    }

    /**
     * minimize the map to include all markers in range
     */
    private void zoomToSpan(List<LatLng> pointList) {
        if (pointList == null || pointList.size() <= 0)
            return;

        if (isAttached()) {
            LatLngBounds bounds = getLatLngBounds(pointList);
            aMapWeakReference.get().moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
        }
    }

    /**
     * 根据自定义内容获取缩放bounds
     */
    private LatLngBounds getLatLngBounds(List<LatLng> pointList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < pointList.size(); i++) {
            LatLng p = pointList.get(i);
            b.include(p);
        }
        return b.build();
    }

}
