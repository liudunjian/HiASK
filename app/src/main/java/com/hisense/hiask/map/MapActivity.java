package com.hisense.hiask.map;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.hisense.hiask.entity.robot.RobotGeoPoiBean;
import com.hisense.hiask.entity.robot.RobotRouteWayBean;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.map.drawer.MapDrawAPI;
import com.hisense.hiask.mvpbase.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/10/29.
 */

public class MapActivity extends BaseActivity<IMapView, MapPresenter> implements IMapView {

    @BindView(R.id.mapView)
    MapView mapView;

    private AMap aMap;
    private MapDrawAPI drawAPI;

    /************base override methods*************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mapView == null) {
            this.finish();
        }
        mapView.onCreate(savedInstanceState);
        initMapView();
    }


    @Override
    protected void initViewDisplay() {
        if (DataTransaction.getInstance().getType() < 0)
            this.finish();
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new MapPresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
        DataTransaction.getInstance().clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drawAPI != null) {
            drawAPI.release();
            drawAPI = null;
        }

        if (mapView != null) {
            mapView.onDestroy();
            mapView = null;
        }

    }

    /**************mvp override methods**************/
    @Override
    public boolean isDataPrepared() {
        return false;
    }

    /*********events and listners*******/

    @OnClick(R.id.robot_title_back)
    public void onViewClicked() {
        this.finish();
    }

    /******************private methods**************/

    private void initMapView() {
        if (aMap == null)
            aMap = mapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        drawAPI = new MapDrawAPI(this, aMap);
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
//        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setMyLocationEnabled(true);

        if (DataTransaction.getInstance().getType() == 0) { //路线
            RobotRouteWayBean item = (RobotRouteWayBean) DataTransaction.getInstance().getInfo();
            if (drawAPI != null && drawAPI.isAttached())
                drawAPI.drawRouteWay(item.getPayload());
        } else if (DataTransaction.getInstance().getType() == 1) {
            RobotGeoPoiBean item = (RobotGeoPoiBean) DataTransaction.getInstance().getInfo();
            if (drawAPI != null && drawAPI.isAttached())
                drawAPI.drawGeoPoi(item.getPayload());
        }

    }


}
