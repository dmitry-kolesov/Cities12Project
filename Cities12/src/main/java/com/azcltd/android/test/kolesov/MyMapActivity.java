package com.azcltd.android.test.kolesov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.utils.GeoPoint;

/**
 * Created by 123 on 16.11.13.
 */
public class MyMapActivity  extends  Activity {

    City city;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_activity);
        final MapView mapView = (MapView) findViewById(R.id.map);
        mapView.showBuiltInScreenButtons(true);

        MapController mMapController = mapView.getMapController();

        Intent intent= getIntent();
        int position = intent.getIntExtra("position", -1);
        if(position >= 0)
        {
             city = WsClient.GetInstance().GetCities().get(position);
            double lat = city.GetLocation().getLatitude();
            double lon = city.GetLocation().getLongitude();
            GeoPoint geoPoint = new GeoPoint(lat, lon);
            mMapController.setPositionAnimationTo(geoPoint);
        }
    }
}
