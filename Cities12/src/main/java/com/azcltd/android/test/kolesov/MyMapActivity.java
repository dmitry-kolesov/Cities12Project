package com.azcltd.android.test.kolesov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
//
//import ru.yandex.yandexmapkit.MapController;
//import ru.yandex.yandexmapkit.MapView;
//import ru.yandex.yandexmapkit.utils.GeoPoint;
//import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
/**
 * Created by kdb on 16.11.13.
 */
public class MyMapActivity  extends  Activity {

    City city;
    MapView mapView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            
//            mapView = new MapView(this, 256);
//            mapView.setClickable(true);
//            mapView.setBuiltInZoomControls(true);
//            setContentView(mapView);
            setContentView(R.layout.map_activity);
            //initYandexMap();
            initOSMDroidMap();

        }catch(Exception ex)
        {
            int j = 0;
        }
    }

    private void initYandexMap()
    {
//        final MapView mapView = (MapView) findViewById(R.id.mapview);//
//        MapController mMapController = mapView.getMapController();
////
//        Intent intent= getIntent();
//        int position = intent.getIntExtra("position", -1);
//        if(position >= 0)
//        {
//            city = WsClient.GetInstance().GetCities().get(position);
//            double lat = city.GetLocation().getLatitude();
//            double lon = city.GetLocation().getLongitude();
//            GeoPoint geoPoint = new GeoPoint(lat, lon);
//            mMapController.setPositionAnimationTo(geoPoint);
//        }
    }
    private void initOSMDroidMap()
    {
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        Intent intent= getIntent();
        int position = intent.getIntExtra("position", -1);
        if(position >= 0)
        {
             city = WsClient.GetInstance().GetCities().get(position);
            double lat = city.GetLocation().getLatitude();
            double lon = city.GetLocation().getLongitude();

            org.osmdroid.views.MapController mapController = (org.osmdroid.views.MapController)mapView.getController();
            mapController.setZoom(15);
            org.osmdroid.util.GeoPoint geoPoint = new org.osmdroid.util.GeoPoint(lat, lon);
            mapController.setCenter(geoPoint);
        }
    }

//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        ResourceProxyImpl mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
//        mapView = new MapView(inflater.getContext(), 256, mResourceProxy);
//        return mapView;
//    }
}
